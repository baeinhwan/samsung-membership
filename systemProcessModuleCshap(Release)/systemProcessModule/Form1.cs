using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Runtime.InteropServices;
using System.Collections;
using System.Net;
using System.Net.Sockets;
using System.Threading;
using System.Runtime.Serialization.Formatters.Binary;
using System.Security.Cryptography;
using System.Runtime.Serialization;


namespace systemProcessModule
{
    public partial class Form1 : Form
    {
        bool serverState = false;   //서버상태
        Socket server;              // 서버 소켓
        Socket client;              // 클라이언트 소켓
        int port = 30001;            // 통신 포트
        Thread threadServer;        // 쓰레드 관리자
        bool bConnected = false;

        bool visualizerServerState = false;   //서버상태
        Socket visualizerServer;              // 서버 소켓
        Socket visualizerclient;              // 클라이언트 소켓
        int visualizerport = 30002;            //가상화 모듈 통신 포트
        bool visualizerbConnected = false;
        Thread visualizerThreadServer; //가상화 모듈 쓰레드
        Thread recvDrivingThread;

        int[] selectedIndex = new int[5];//고장상태를 가지고 있는 변수 0번부터 주행,전력,휠서보,무장,통합냉각으로 이루어 진다.
        short[] wheelRpm = new short[2];//바퀴 회전 속도를 가지고 있는 변수 0번은 좌측 1번은 우측

        short DUP_STR_CMD = 0; //임시변수 나중에 삭제

        string value;
        //TPacket tp = new TPacket(); //통신패킷

        //접속한 컨트롤러의 IP
        string clientIP = "";

        int PREV_RPM_CMD = 0;//처음의 차량의 속도
        int PREV_RPM_BRK_CMD = 0;//처음에 브레이크 지수
        int PREV_PRM_STR_CMD = 0;//처음에 조향 지수

        short DRV_CMD = 0;//사용자에게 입력받는 엑셀 수치
        short BRK_CMD = 0;//사용자에게 입력받는 제동 수치
        short STR_CMD = 0;//사용자에게 입력받는 조향 수치

        byte EmergencyEnable = 0x00;//비상정지 명령의 상태
        byte OperationMode = 0x01;//기동모드 명령의 상태
        byte DrivingMode = 0x01;// 주행모드 명령의 상태
        int DrivingDirection = 1;//현재 주행의 방향을 설정 0 중립, 1 전진, -1 후진d

        //int gear = 1;//RPM에 곱해서 전진 중립 후진으로 사용 전진= 1 중립 =0 후진 =-1

        public Form1()
        {
            InitializeComponent();
        }





        private void DUPfuel_SelectedItemChanged(object sender, EventArgs e)
        {

        }

        //서버 가동/종료 함수p
        private void btnStart_Click(object sender, EventArgs e)
        {
            //서버 시작시
            if (serverState == false)
            {
                //서버 상태값 변경
                serverState = true;

                try
                {
                    //소켓 오픈
                    server = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

                    // IPHostEntry host = Dns.Resolve(Dns.GetHostName());
                    // string strIP = host.AddressList[0].ToString();         // Get the local IP address
                    // IPAddress hostIP = IPAddress.Parse(strIP);

                    // IPEndPoint ep = new IPEndPoint(hostIP, port);
                    IPEndPoint ep = new IPEndPoint(IPAddress.Any, port);

                    server.Bind(ep);
                    server.Blocking = true;     // The server socket is working in blocking mode
                    server.Listen(1);           //하나의 접속만 허용
                }
                catch (SocketException exc)
                {
                    MessageBox.Show(exc.ToString());
                }

                //서버 쓰레드 시작
                threadServer = new Thread(new ThreadStart(ServerProc));
                threadServer.Start();
                ReportTimer.Start();
                set_rpm_timer.Start();
                
                //컴포넌트 값 변경
                btnStart.Text = "Server Stop";
                MessageBox.Show("서버가 가동 되었습니다.");
            }
            else
            {

                //서버 상태값 변경
                serverState = false;
                //소켓 클로징
                server.Close();

                //쓰레드 중지
                threadServer.Abort();
                set_rpm_timer.Stop();
                
                btnStart.Text = "Server Start";
                MessageBox.Show("서버가 종료 되었습니다.");
            }
        }


        //서버의 메인 작업을 하는함수
        //스레드로 실행되게 된다.
        private void ServerProc()
        {
            byte[] pByteArr = new byte[9]; //헤더 버퍼
            Hpacket hpacket = new Hpacket(); //헤더 패킷내용
            //서비스가 중지될 때 까지 계속 반복

            while (true)
            {
                for (int i = 0; i < 9; i++)
                    pByteArr[i] = 0;

                // If a client is not connected
                if (bConnected == false)
                {
                    if (serverState == false)
                        return;

                    client = server.Accept();
                    
                    MessageBox.Show("서버가 연결 되었습니다.");
                    bConnected = true;

                }
                // If a client is connected, wait for data from client
                else
                {
                    //클라이언트 아이피 획득
                    if (clientIP == "")
                        clientIP = client.RemoteEndPoint.ToString();

                    int len;
                    try
                    {
                        //헤더 전송 받음
                        len = client.Receive(pByteArr, 0, 9, 0);

                        if (len == 0)       // If the client is disconnected
                        {
                            // bConnected = false;

                            client.Disconnect(true);
                            //this.Invoke(new EventHandler(UpdateServerUI));
                        }
                        else                // If data from client is arrived
                        {
                            //도착한 헤더를 확인
                            switch (pByteArr[0])
                            {
                                case Hpacket.EmergencyStop: //1.2.1 비상정지 명령
                                    EmergencyEnable = pByteArr[1];    
                                //EmergencyStop(pByteArr);
                                    break;
                                case Hpacket.OPERATION_MODE://1.2.2 기동모드 명령
                                    OperationMode = pByteArr[1];    
                                //RecvOperationMode(pByteArr);
                                    break;
                                case Hpacket.DRIVING_MODE:  //1.2.3 주행모드 명령
                                    DrivingMode = pByteArr[1];    
                                //RecvDrivingMode(pByteArr);
                                    break;
                                case Hpacket.CRUISE_CONTROL://1.2.4 주행제어 명령
                                    RecvDrivingRpm(pByteArr);
                                    break;
                            }

                        }

                        //threadServer = new Thread(new ThreadStart(ServerProc));
                        //threadServer.Start();
                    }
                    catch (SocketException exc)
                    {
                        clientIP = "";
                        bConnected = false;
                        //ReportTimer.Stop();
                    }
                }
            }
        }




        //form초기화
        private void Form1_Load(object sender, EventArgs e)
        {
            //FUEL,COOLANT,SOC 수치 초기화 
            DUPfuel.Text = "0";
            DUPcoolant.Text = "0";
            DUPsoc.Text = "0";
            EquipmentStatus1.Text = "Normal";
            EquipmentStatus2.Text = "Normal";
            EquipmentStatus3.Text = "Normal";
            EquipmentStatus4.Text = "Normal";
            EquipmentStatus5.Text = "Normal";

        }



        //////////////////////SControl정보 처리함수//////////////////
        //수신된 패킷의 헤더를 확인후 해단되는 함수로 들어오게 된다.
        //////////////차량 제어를 위한 함수///////////

        /*

        //1.2.1 비상정지 명령 처리 함수
        public void EmergencyStop(byte [] pByteArr)
        {
        
            int len;
            byte[] pByteArr = new byte[6]; //헤더 버퍼
            for (int i = 0; i < 6; i++)
                pByteArr[i] = 0;

            len = client.Receive(pByteArr, 0, 1, 0);
           
            EmergencyEnable = pByteArr[1]; //컨트롤러에서 비상정지 명령을 수신

        }

        //1.2.2기동모드 명령 처리 함수
        //미완
        public void RecvOperationMode()
        {
            int len;
            byte[] pByteArr = new byte[6]; //헤더 버퍼
            for (int i = 0; i < 6; i++)
                pByteArr[i] = 0;

            len = client.Receive(pByteArr, 0, 1, 0);
            OperationMode = pByteArr[0];



        }

        //1.2.3주행모드 명령 처리 함수
        public void RecvDrivingMode()
        {
            int len;
            byte[] pByteArr = new byte[6]; //헤더 버퍼
            for (int i = 0; i < 6; i++)
                pByteArr[i] = 0;

            len = client.Receive(pByteArr, 0, 1, 0);
            DrivingMode = pByteArr[0];


        }

        */

        //1.2.4주행제어 명령 처리 함수
        //Header = 0x46
        public void RecvDrivingRpm(byte[] pByteArr)
        {
            /*
            int len;

            byte[] pByteArr = new byte[6]; //헤더 버퍼
            for (int i = 0; i < 6; i++)
                pByteArr[i] = 0;

            len = client.Receive(pByteArr, 0, 6, 0);
            */
            //처음에 비상 정지 명령이 하달 되었는지 확인
            //1.2.1비상정지 명령이 켜져 있으면 사용자의 입력 무시
            //1.2.2기동모드가 유인기동이 아닐경우 사용자의 입력 무시
            if ((EmergencyEnable == 0xFF) || (OperationMode != 0x03))
            {
                return;
            }

            DRV_CMD = BitConverter.ToInt16(pByteArr, 1);
            short buf = 0;
            buf = BitConverter.ToInt16(pByteArr, 3);
            STR_CMD = (short)((buf * 2) - 100); //0~100사이로 들어온 조향 값을 -100~100사이로 조정한다.
            DUP_STR_CMD = STR_CMD;
            BRK_CMD = BitConverter.ToInt16(pByteArr, 5);
            


        }

        //사용자에게 입력받은 rpm으로 100ms마다 수정
        private void set_rpm_timer_Tick(object sender, EventArgs e)
        {
            SetDrivingSpeed();
            SetDrivingMode();
        }
        private void SetDrivingMode()
        {
            switch (EmergencyEnable)
            {
                case 0xFF: //ON
                    DRV_CMD = 0;
                    STR_CMD = 0;
                    BRK_CMD = 100;
                    EmergencyOutput.Text = "ON";
                    break;
                case 0x00: //OFF       
                    EmergencyOutput.Text = "OFF";
                    break;
            }

            switch (OperationMode)
            {
                case 0x01: //기동파킹    
                    DRV_CMD = 0;
                    STR_CMD = 0;
                    BRK_CMD = 100;
                    OPModeOutput.Text = "기동파킹";
                    break;
                case 0x02: //유인기동
                    DRV_CMD = 0;
                    STR_CMD = 0;
                    BRK_CMD = 100;
                    OPModeOutput.Text = "유인기동";
                    break;
                case 0x03: //무인기동
                    OPModeOutput.Text = "무인기동";
                    break;
            }

            switch (DrivingMode)
            {
                case 0x01: //중립
                    DrivingDirection = 0;
                    DrivingModeOutput.Text = "중립";
                    break;
                case 0x02: //후진
                    DrivingDirection = -1;
                    DrivingModeOutput.Text = "후진";
                    break;
                case 0x03: //전진
                    DrivingDirection = 1;
                    DrivingModeOutput.Text = "전진";
                    break;
                case 0x04: //선회   //미완성
                    DrivingModeOutput.Text = "선회";
                    DrivingDirection = 1;
                    break;
            }
        }

        public void SetDrivingSpeed()
        {

            int DRV_TO_RPM = 60;//RPM변화 상수

            int BRK_TO_RPM = 20;//RPM변화 상수

            int STR_TO_RPM = 20;//RPM변화 상수
            //최대 변화량은 1초단위의 수치인데 100ms마다 호출되므로 interval별로 나누게 된다.
            int DRV_SLEW_RATE = 300 * set_rpm_timer.Interval / 1000; //초당 최대 구동 RPM 변화량(RPM/S)
            int BRK_SLEW_RATE = 1800 * set_rpm_timer.Interval / 1000; //초당 최대 제동 RPM 변화량(RPM/S)
            int STR_SLEW_RATE = 200 * set_rpm_timer.Interval / 1000; //초당 최대 조향 RPM 변화량(RPM/S)

            //현재 주행제어 명령정보를 Form에 전시
            SteerOutput.Text = STR_CMD.ToString() + "%";
            DriveOutput.Text = DRV_CMD.ToString() + "%";
            BrakeOutput.Text = BRK_CMD.ToString() + "%";



            //구동명령을 RPM단위로 변환
            int RPM_CMD;
            RPM_CMD = OrderToRpm(DRV_CMD, DRV_TO_RPM, PREV_RPM_CMD, DRV_SLEW_RATE);
            PREV_RPM_CMD = RPM_CMD;
            //제동명령을 RPM단위로 변환
            int RPM_BRK_CMD;
            RPM_BRK_CMD = OrderToRpm(BRK_CMD, BRK_TO_RPM, PREV_RPM_BRK_CMD, BRK_SLEW_RATE);
            PREV_RPM_BRK_CMD = RPM_BRK_CMD;
            //제동 명령에 따른 RPM감속
            RPM_BRK_CMD = RPM_CMD - RPM_BRK_CMD;
            RPM_CMD = RPM_CMD < 0 ? 0 : RPM_CMD;
            //조향명령을 RPM단위로 변환
            int RPM_STR_CMD;
            RPM_STR_CMD = OrderToRpm(STR_CMD, STR_TO_RPM, PREV_PRM_STR_CMD, STR_SLEW_RATE);
            PREV_PRM_STR_CMD = RPM_STR_CMD;
            //조향 명령에 따른 좌우 휠 RPM증감
            Set_Wheel(RPM_CMD, RPM_STR_CMD);
            //챠량속도 산출
            int VELCOCITY;
            VELCOCITY = RPM_CMD / 100 * DrivingDirection;
            VeloOutput.Text = VELCOCITY.ToString() + " kph";
            //차량자세 산출(-20.0~20.0)
            int YAW = RPM_STR_CMD / 100;
        }
        //조향명령에 따른 좌우 휠 RPM증감
        private void Set_Wheel(int RPM_CMD, int RPM_STR_CMD)
        {
            Left_Wheel1.Text = ((RPM_CMD + RPM_STR_CMD) * DrivingDirection).ToString() + " rpm";
            Left_Wheel2.Text = ((RPM_CMD + RPM_STR_CMD) * DrivingDirection).ToString() + " rpm";
            Left_Wheel3.Text = ((RPM_CMD + RPM_STR_CMD) * DrivingDirection).ToString() + " rpm";
            wheelRpm[0] = (short)((RPM_CMD + RPM_STR_CMD) * DrivingDirection);
            Right_Wheel1.Text = ((RPM_CMD - RPM_STR_CMD) * DrivingDirection).ToString() + " rpm";
            Right_Wheel2.Text = ((RPM_CMD - RPM_STR_CMD) * DrivingDirection).ToString() + " rpm";
            Right_Wheel3.Text = ((RPM_CMD - RPM_STR_CMD) * DrivingDirection).ToString() + " rpm";
            wheelRpm[1] = (short)((RPM_CMD - RPM_STR_CMD) * DrivingDirection);
        }


        //최종 RPM변화량을 구한다.
        //Parameter 사용자명령,변환상수,이전RPM,최대 변화량   
        //Return Value 최종 RPM변화량
        private int OrderToRpm(short DRV_CMD, int DRV_TO_RPM, int PREV_RPM_CMD, int SLEW_RATE)
        {

            int RPM_CMD;
            RPM_CMD = (int)DRV_CMD * DRV_TO_RPM;

            //최대 RPM변화량 적용
            int DIFF;
            DIFF = RPM_CMD - PREV_RPM_CMD;
            RPM_CMD = ApplySlewRate(PREV_RPM_CMD, DIFF, SLEW_RATE);
            return RPM_CMD;
        }

        //RPM이 변화하는게 최대 변화량 이하로 변하도록 결과 출력
        private int ApplySlewRate(int input, int delta, int slew_rate)
        {
            int output = 0;
            if ((-slew_rate < delta) && (delta < slew_rate))
            {
                output = input + delta;
            }
            else if (delta >= slew_rate)
            {
                output = input + slew_rate;
            }
            else if (delta <= -slew_rate)
            {
                output = input - slew_rate;
            }
            return output;
        }



        ///////////////////////////가시화 모듈 부분/////////////////////////////////////////

        private void btnVisualizerStart_Click(object sender, EventArgs e)
        {
            //서버 시작시
            if (visualizerServerState == false)
            {
                //서버 상태값 변경
                visualizerServerState = true;

                try
                {
                    //소켓 오픈
                    visualizerServer = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

                    // IPHostEntry host = Dns.Resolve(Dns.GetHostName());
                    // string strIP = host.AddressList[0].ToString();         // Get the local IP address
                    // IPAddress hostIP = IPAddress.Parse(strIP);

                    // IPEndPoint ep = new IPEndPoint(hostIP, port);
                    IPEndPoint ep = new IPEndPoint(IPAddress.Any, visualizerport);

                    visualizerServer.Bind(ep);
                    visualizerServer.Blocking = true;     // The server socket is working in blocking mode
                    visualizerServer.Listen(1);           //하나의 접속만 허용
                }
                catch (SocketException exc)
                {
                    MessageBox.Show(exc.ToString());
                }

                //서버 쓰레드 시작
                visualizerThreadServer = new Thread(new ThreadStart(visualizerServerProc));
                visualizerThreadServer.Start();



                //컴포넌트 값 변경
                btnVisualizerStart.Text = "Server Stop";
                MessageBox.Show("가시화 서버가 가동 되었습니다.");
            }
            else
            {
                //서버 상태값 변경
                visualizerServerState = false;

                //스레드 종료
                visualizerThreadServer.Abort();
                //소켓 클로징
                visualizerServer.Close();
                //VisualizerTimer.Stop();

                btnVisualizerStart.Text = "Server Start";
                MessageBox.Show("가시화 서버가 종료 되었습니다.");
            }
        }

        //가시화 모듈 스레드 함수
        private void visualizerServerProc()
        {

            visualizerclient = visualizerServer.Accept();
            
                    MessageBox.Show("가시화 서버가 연결 되었습니다.");
                    VisualizerTimer.Start();
                  
            while (true)
                    {
                        Thread.Sleep(1000);
                        VisualizerTransport();
                    }
            
        }

        private void VisualizerTransport()
        {
            byte[] pByteArr = new byte[9]; //헤더 버퍼
            for (int i = 0; i < 9; i++)
                pByteArr[i] = 0;


            pByteArr[0] = 0xB8;
            short velo = (short)(PREV_RPM_CMD / 100 * 10);//rpm/100*10으로 보내면 가시화에서 10으로 나누어 사용
            short yaw = (short)(STR_CMD * 2); //-200~200사이로 보내면 가시화에서 10으로 나누어 사용
            pByteArr[2] = (byte)(velo >> 8);
            pByteArr[1] = (byte)(velo & 0xff);
            pByteArr[4] = (byte)(yaw >> 8);
            pByteArr[3] = (byte)(yaw & 0xff);

            
            visualizerclient.Send(pByteArr, 0, 9, 0);

        }

        //short 속도, short 방향을 가시화 모듈로 전송
        private void VisualizerTimer_Tick(object sender, EventArgs e)
        {
        }

        ///////////////////////////////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////차량상태 보고 부분///////////////

        private void ReportTimer_Tick(object sender, EventArgs e)
        {
            if (bConnected == true)
            {
                sendDriveReport(); //1.2.8 차량상태보고
                Thread.Sleep(100);
                sendRpm();//1.2.9 좌우 휠별 구동력
            }
        }

        //임시로 좌측 우측 한번에 하는걸로 작성 나중에 수정 해야함
        private void sendRpm()
        {
            //-부호를 없애기 위해 10000을 더했다가 Scontrol에서는 뺀다.
             byte[] pByteArr = new byte[9]; //헤더 버퍼
            for (int i = 0; i < 9; i++)
                pByteArr[i] = 0;
            pByteArr[0] = 0x96;
            pByteArr[2] = (byte)((wheelRpm[0]+10000) >> 8);
            pByteArr[1] = (byte)((wheelRpm[0]+10000) & 0xff);
            pByteArr[4] = (byte)((wheelRpm[1]+10000) >> 8);
            pByteArr[3] = (byte)((wheelRpm[1]+10000) & 0xff);

            try
            {
                client.Send(pByteArr, 0, 9, 0);
            }
            catch (SocketException e)
            {

            }
        }

        private void sendDriveReport()
        {
            byte[] pByteArr = new byte[9]; //헤더 버퍼
            for (int i = 0; i < 9; i++)
                pByteArr[i] = 0;



            short status;
            status = equipmentStatus();// 현재 고장상황을 확인


            pByteArr[0] = Hpacket.VEHICLE_STATUS_REPORT;
            byte velo = (byte)(PREV_RPM_CMD / 100 *DrivingDirection); //0~60kph
            pByteArr[1] = velo;
            pByteArr[2] = (byte)Convert.ToInt32(DUPfuel.Text);
            pByteArr[3] = (byte)Convert.ToInt32(DUPsoc.Text);
            pByteArr[4] = (byte)Convert.ToInt32(DUPcoolant.Text);
            pByteArr[6] = (byte)(status >> 8);
            pByteArr[5] = (byte)(status & 0xff);
            try
            {
                client.Send(pByteArr, 0, 9, 0);
            }
            catch (SocketException e)
            {

            }
        }
        
        
        private void button1_Click(object sender, EventArgs e)
        {
            byte[] pByteArr = new byte[9]; //헤더 버퍼
            for (int i = 0; i < 9; i++)
                pByteArr[i] = 0;



            short status;
            status = equipmentStatus();// 현재 고장상황을 확인


            pByteArr[0] = Hpacket.VEHICLE_STATUS_REPORT;
            byte velo = (byte)(PREV_RPM_CMD / 100); //0~60kph
            pByteArr[1] = velo;
            pByteArr[2] = (byte)Convert.ToInt32(DUPfuel.Text);
            pByteArr[3] = (byte)Convert.ToInt32(DUPsoc.Text);
            pByteArr[4] = (byte)Convert.ToInt32(DUPcoolant.Text);
            pByteArr[6] = (byte)(status >> 8);
            pByteArr[5] = (byte)(status & 0xff);

            client.Send(pByteArr, 0, 9, 0);
        }
        
        private short equipmentStatus()
        {
            short status = 0;
       
            for (int i = 0; i < 5; i++)
            {
                status += (short)(selectedIndex[i] * Math.Pow(4, i));
            }

                return status;
        }

        private void EquipmentStatus1_SelectedIndexChanged(object sender, EventArgs e)
        {
            selectedIndex[0] = EquipmentStatus1.SelectedIndex;
        }

        private void EquipmentStatus2_SelectedIndexChanged(object sender, EventArgs e)
        {
            selectedIndex[1] = EquipmentStatus2.SelectedIndex;
        }

        private void EquipmentStatus3_SelectedIndexChanged(object sender, EventArgs e)
        {
            selectedIndex[2] = EquipmentStatus3.SelectedIndex;
        }

        private void EquipmentStatus4_SelectedIndexChanged(object sender, EventArgs e)
        {
            selectedIndex[3] = EquipmentStatus4.SelectedIndex;
        }

        private void EquipmentStatus5_SelectedIndexChanged(object sender, EventArgs e)
        {
            selectedIndex[4] = EquipmentStatus5.SelectedIndex;
        }

       
    }
}
