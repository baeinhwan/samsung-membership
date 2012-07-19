namespace systemProcessModule
{
    partial class Form1
    {
        /// <summary>
        /// 필수 디자이너 변수입니다.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// 사용 중인 모든 리소스를 정리합니다.
        /// </summary>
        /// <param name="disposing">관리되는 리소스를 삭제해야 하면 true이고, 그렇지 않으면 false입니다.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form 디자이너에서 생성한 코드

        /// <summary>
        /// 디자이너 지원에 필요한 메서드입니다.
        /// 이 메서드의 내용을 코드 편집기로 수정하지 마십시오.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.lab_fuel = new System.Windows.Forms.Label();
            this.lab_SOC = new System.Windows.Forms.Label();
            this.dsa = new System.Windows.Forms.Label();
            this.DUPfuel = new System.Windows.Forms.DomainUpDown();
            this.DUPsoc = new System.Windows.Forms.DomainUpDown();
            this.DUPcoolant = new System.Windows.Forms.DomainUpDown();
            this.label2 = new System.Windows.Forms.Label();
            this.SteerOutput = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.label7 = new System.Windows.Forms.Label();
            this.label8 = new System.Windows.Forms.Label();
            this.label9 = new System.Windows.Forms.Label();
            this.Left_Wheel1 = new System.Windows.Forms.Label();
            this.Left_Wheel2 = new System.Windows.Forms.Label();
            this.Left_Wheel3 = new System.Windows.Forms.Label();
            this.label13 = new System.Windows.Forms.Label();
            this.label14 = new System.Windows.Forms.Label();
            this.label15 = new System.Windows.Forms.Label();
            this.label16 = new System.Windows.Forms.Label();
            this.label17 = new System.Windows.Forms.Label();
            this.label18 = new System.Windows.Forms.Label();
            this.DCUStatus = new System.Windows.Forms.ComboBox();
            this.PCUStatus = new System.Windows.Forms.ComboBox();
            this.MCUStatus = new System.Windows.Forms.ComboBox();
            this.FCUStatus = new System.Windows.Forms.ComboBox();
            this.TCUStatus = new System.Windows.Forms.ComboBox();
            this.label19 = new System.Windows.Forms.Label();
            this.VeloOutput = new System.Windows.Forms.Label();
            this.OPModeOutput = new System.Windows.Forms.Label();
            this.DrivingModeOutput = new System.Windows.Forms.Label();
            this.EmergencyOutput = new System.Windows.Forms.Label();
            this.btnStart = new System.Windows.Forms.Button();
            this.DriveOutput = new System.Windows.Forms.Label();
            this.Output = new System.Windows.Forms.Label();
            this.BrakeOutput = new System.Windows.Forms.Label();
            this.Right_Wheel3 = new System.Windows.Forms.Label();
            this.Right_Wheel2 = new System.Windows.Forms.Label();
            this.Right_Wheel1 = new System.Windows.Forms.Label();
            this.set_rpm_timer = new System.Windows.Forms.Timer(this.components);
            this.btnVisualizerStart = new System.Windows.Forms.Button();
            this.VisualizerTimer = new System.Windows.Forms.Timer(this.components);
            this.ReportTimer = new System.Windows.Forms.Timer(this.components);
            this.SuspendLayout();
            // 
            // lab_fuel
            // 
            this.lab_fuel.AutoSize = true;
            this.lab_fuel.Location = new System.Drawing.Point(19, 45);
            this.lab_fuel.Name = "lab_fuel";
            this.lab_fuel.Size = new System.Drawing.Size(29, 12);
            this.lab_fuel.TabIndex = 0;
            this.lab_fuel.Text = "Fuel";
            // 
            // lab_SOC
            // 
            this.lab_SOC.AutoSize = true;
            this.lab_SOC.Location = new System.Drawing.Point(17, 72);
            this.lab_SOC.Name = "lab_SOC";
            this.lab_SOC.Size = new System.Drawing.Size(31, 12);
            this.lab_SOC.TabIndex = 1;
            this.lab_SOC.Text = "SOC";
            // 
            // dsa
            // 
            this.dsa.AutoSize = true;
            this.dsa.Location = new System.Drawing.Point(10, 99);
            this.dsa.Name = "dsa";
            this.dsa.Size = new System.Drawing.Size(48, 12);
            this.dsa.TabIndex = 2;
            this.dsa.Text = "Coolant";
            // 
            // DUPfuel
            // 
            this.DUPfuel.Items.Add("100");
            this.DUPfuel.Items.Add("95");
            this.DUPfuel.Items.Add("90");
            this.DUPfuel.Items.Add("85");
            this.DUPfuel.Items.Add("80");
            this.DUPfuel.Items.Add("75");
            this.DUPfuel.Items.Add("70");
            this.DUPfuel.Items.Add("65");
            this.DUPfuel.Items.Add("60");
            this.DUPfuel.Items.Add("55");
            this.DUPfuel.Items.Add("50");
            this.DUPfuel.Items.Add("45");
            this.DUPfuel.Items.Add("40");
            this.DUPfuel.Items.Add("35");
            this.DUPfuel.Items.Add("30");
            this.DUPfuel.Items.Add("25");
            this.DUPfuel.Items.Add("20");
            this.DUPfuel.Items.Add("15");
            this.DUPfuel.Items.Add("10");
            this.DUPfuel.Items.Add("5");
            this.DUPfuel.Items.Add("0");
            this.DUPfuel.Location = new System.Drawing.Point(67, 36);
            this.DUPfuel.Name = "DUPfuel";
            this.DUPfuel.Size = new System.Drawing.Size(82, 21);
            this.DUPfuel.TabIndex = 3;
            this.DUPfuel.Text = "0";
            this.DUPfuel.SelectedItemChanged += new System.EventHandler(this.DUPfuel_SelectedItemChanged);
            // 
            // DUPsoc
            // 
            this.DUPsoc.Items.Add("100");
            this.DUPsoc.Items.Add("95");
            this.DUPsoc.Items.Add("90");
            this.DUPsoc.Items.Add("85");
            this.DUPsoc.Items.Add("80");
            this.DUPsoc.Items.Add("75");
            this.DUPsoc.Items.Add("70");
            this.DUPsoc.Items.Add("65");
            this.DUPsoc.Items.Add("60");
            this.DUPsoc.Items.Add("55");
            this.DUPsoc.Items.Add("50");
            this.DUPsoc.Items.Add("45");
            this.DUPsoc.Items.Add("40");
            this.DUPsoc.Items.Add("35");
            this.DUPsoc.Items.Add("30");
            this.DUPsoc.Items.Add("25");
            this.DUPsoc.Items.Add("20");
            this.DUPsoc.Items.Add("15");
            this.DUPsoc.Items.Add("10");
            this.DUPsoc.Items.Add("5");
            this.DUPsoc.Items.Add("0");
            this.DUPsoc.Location = new System.Drawing.Point(67, 63);
            this.DUPsoc.Name = "DUPsoc";
            this.DUPsoc.Size = new System.Drawing.Size(82, 21);
            this.DUPsoc.TabIndex = 4;
            this.DUPsoc.Text = "0";
            // 
            // DUPcoolant
            // 
            this.DUPcoolant.Items.Add("130 ");
            this.DUPcoolant.Items.Add("125");
            this.DUPcoolant.Items.Add("120");
            this.DUPcoolant.Items.Add("115");
            this.DUPcoolant.Items.Add("110");
            this.DUPcoolant.Items.Add("105");
            this.DUPcoolant.Items.Add("100");
            this.DUPcoolant.Items.Add("95");
            this.DUPcoolant.Items.Add("90");
            this.DUPcoolant.Items.Add("85");
            this.DUPcoolant.Items.Add("80");
            this.DUPcoolant.Items.Add("75");
            this.DUPcoolant.Items.Add("70");
            this.DUPcoolant.Items.Add("65");
            this.DUPcoolant.Items.Add("60");
            this.DUPcoolant.Items.Add("55");
            this.DUPcoolant.Items.Add("50");
            this.DUPcoolant.Items.Add("45");
            this.DUPcoolant.Items.Add("40");
            this.DUPcoolant.Items.Add("35");
            this.DUPcoolant.Items.Add("30");
            this.DUPcoolant.Items.Add("25");
            this.DUPcoolant.Items.Add("20");
            this.DUPcoolant.Items.Add("15");
            this.DUPcoolant.Items.Add("10");
            this.DUPcoolant.Items.Add("5");
            this.DUPcoolant.Items.Add("0");
            this.DUPcoolant.Location = new System.Drawing.Point(67, 90);
            this.DUPcoolant.Name = "DUPcoolant";
            this.DUPcoolant.Size = new System.Drawing.Size(82, 21);
            this.DUPcoolant.TabIndex = 5;
            this.DUPcoolant.Text = "0";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(10, 190);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(78, 12);
            this.label2.TabIndex = 6;
            this.label2.Text = "Steer CMD : ";
            // 
            // SteerOutput
            // 
            this.SteerOutput.AutoSize = true;
            this.SteerOutput.Location = new System.Drawing.Point(82, 190);
            this.SteerOutput.Name = "SteerOutput";
            this.SteerOutput.Size = new System.Drawing.Size(21, 12);
            this.SteerOutput.TabIndex = 7;
            this.SteerOutput.Text = "0%";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(171, 45);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(62, 12);
            this.label4.TabIndex = 8;
            this.label4.Text = "Velocity : ";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(171, 99);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(91, 12);
            this.label5.TabIndex = 9;
            this.label5.Text = "Driving Mode : ";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(171, 72);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(107, 12);
            this.label6.TabIndex = 10;
            this.label6.Text = "Operation Mode : ";
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(171, 123);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(78, 12);
            this.label7.TabIndex = 11;
            this.label7.Text = "Emergency :";
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Location = new System.Drawing.Point(109, 190);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(77, 12);
            this.label8.TabIndex = 12;
            this.label8.Text = "Drive CMD : ";
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Location = new System.Drawing.Point(339, 20);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(65, 12);
            this.label9.TabIndex = 13;
            this.label9.Text = "Left-Wheel";
            // 
            // Left_Wheel1
            // 
            this.Left_Wheel1.AutoSize = true;
            this.Left_Wheel1.Location = new System.Drawing.Point(339, 45);
            this.Left_Wheel1.Name = "Left_Wheel1";
            this.Left_Wheel1.Size = new System.Drawing.Size(33, 12);
            this.Left_Wheel1.TabIndex = 14;
            this.Left_Wheel1.Text = "0rpm";
            // 
            // Left_Wheel2
            // 
            this.Left_Wheel2.AutoSize = true;
            this.Left_Wheel2.Location = new System.Drawing.Point(339, 69);
            this.Left_Wheel2.Name = "Left_Wheel2";
            this.Left_Wheel2.Size = new System.Drawing.Size(33, 12);
            this.Left_Wheel2.TabIndex = 15;
            this.Left_Wheel2.Text = "0rpm";
            // 
            // Left_Wheel3
            // 
            this.Left_Wheel3.AutoSize = true;
            this.Left_Wheel3.Location = new System.Drawing.Point(339, 92);
            this.Left_Wheel3.Name = "Left_Wheel3";
            this.Left_Wheel3.Size = new System.Drawing.Size(33, 12);
            this.Left_Wheel3.TabIndex = 16;
            this.Left_Wheel3.Text = "0rpm";
            // 
            // label13
            // 
            this.label13.AutoSize = true;
            this.label13.Location = new System.Drawing.Point(440, 20);
            this.label13.Name = "label13";
            this.label13.Size = new System.Drawing.Size(71, 12);
            this.label13.TabIndex = 17;
            this.label13.Text = "Fault Status";
            // 
            // label14
            // 
            this.label14.AutoSize = true;
            this.label14.Location = new System.Drawing.Point(407, 45);
            this.label14.Name = "label14";
            this.label14.Size = new System.Drawing.Size(30, 12);
            this.label14.TabIndex = 18;
            this.label14.Text = "DCU";
            // 
            // label15
            // 
            this.label15.AutoSize = true;
            this.label15.Location = new System.Drawing.Point(407, 72);
            this.label15.Name = "label15";
            this.label15.Size = new System.Drawing.Size(30, 12);
            this.label15.TabIndex = 19;
            this.label15.Text = "PCU";
            // 
            // label16
            // 
            this.label16.AutoSize = true;
            this.label16.Location = new System.Drawing.Point(407, 94);
            this.label16.Name = "label16";
            this.label16.Size = new System.Drawing.Size(33, 12);
            this.label16.TabIndex = 20;
            this.label16.Text = "MCU";
            // 
            // label17
            // 
            this.label17.AutoSize = true;
            this.label17.Location = new System.Drawing.Point(407, 123);
            this.label17.Name = "label17";
            this.label17.Size = new System.Drawing.Size(29, 12);
            this.label17.TabIndex = 21;
            this.label17.Text = "FCU";
            // 
            // label18
            // 
            this.label18.AutoSize = true;
            this.label18.Location = new System.Drawing.Point(407, 146);
            this.label18.Name = "label18";
            this.label18.Size = new System.Drawing.Size(30, 12);
            this.label18.TabIndex = 22;
            this.label18.Text = "TCU";
            // 
            // DCUStatus
            // 
            this.DCUStatus.FormattingEnabled = true;
            this.DCUStatus.Items.AddRange(new object[] {
            "Normal",
            "Fault",
            "Warning",
            "Stop"});
            this.DCUStatus.Location = new System.Drawing.Point(443, 42);
            this.DCUStatus.Name = "DCUStatus";
            this.DCUStatus.Size = new System.Drawing.Size(87, 20);
            this.DCUStatus.TabIndex = 23;
            // 
            // PCUStatus
            // 
            this.PCUStatus.FormattingEnabled = true;
            this.PCUStatus.Items.AddRange(new object[] {
            "Normal",
            "Fault",
            "Warning",
            "Stop"});
            this.PCUStatus.Location = new System.Drawing.Point(443, 69);
            this.PCUStatus.Name = "PCUStatus";
            this.PCUStatus.Size = new System.Drawing.Size(87, 20);
            this.PCUStatus.TabIndex = 24;
            this.PCUStatus.SelectedIndexChanged += new System.EventHandler(this.comboBox2_SelectedIndexChanged);
            // 
            // MCUStatus
            // 
            this.MCUStatus.FormattingEnabled = true;
            this.MCUStatus.Items.AddRange(new object[] {
            "Normal",
            "Fault",
            "Warning",
            "Stop"});
            this.MCUStatus.Location = new System.Drawing.Point(443, 94);
            this.MCUStatus.Name = "MCUStatus";
            this.MCUStatus.Size = new System.Drawing.Size(87, 20);
            this.MCUStatus.TabIndex = 25;
            // 
            // FCUStatus
            // 
            this.FCUStatus.FormattingEnabled = true;
            this.FCUStatus.Items.AddRange(new object[] {
            "Normal",
            "Fault",
            "Warning",
            "Stop"});
            this.FCUStatus.Location = new System.Drawing.Point(443, 117);
            this.FCUStatus.Name = "FCUStatus";
            this.FCUStatus.Size = new System.Drawing.Size(87, 20);
            this.FCUStatus.TabIndex = 26;
            // 
            // TCUStatus
            // 
            this.TCUStatus.FormattingEnabled = true;
            this.TCUStatus.Items.AddRange(new object[] {
            "Normal",
            "Fault",
            "Warning",
            "Stop"});
            this.TCUStatus.Location = new System.Drawing.Point(443, 143);
            this.TCUStatus.Name = "TCUStatus";
            this.TCUStatus.Size = new System.Drawing.Size(87, 20);
            this.TCUStatus.TabIndex = 27;
            // 
            // label19
            // 
            this.label19.AutoSize = true;
            this.label19.Location = new System.Drawing.Point(545, 20);
            this.label19.Name = "label19";
            this.label19.Size = new System.Drawing.Size(73, 12);
            this.label19.TabIndex = 28;
            this.label19.Text = "Right-Wheel";
            // 
            // VeloOutput
            // 
            this.VeloOutput.AutoSize = true;
            this.VeloOutput.Location = new System.Drawing.Point(240, 45);
            this.VeloOutput.Name = "VeloOutput";
            this.VeloOutput.Size = new System.Drawing.Size(31, 12);
            this.VeloOutput.TabIndex = 29;
            this.VeloOutput.Text = "0kph";
            // 
            // OPModeOutput
            // 
            this.OPModeOutput.AutoSize = true;
            this.OPModeOutput.Location = new System.Drawing.Point(284, 72);
            this.OPModeOutput.Name = "OPModeOutput";
            this.OPModeOutput.Size = new System.Drawing.Size(43, 12);
            this.OPModeOutput.TabIndex = 30;
            this.OPModeOutput.Text = "Default";
            // 
            // DrivingModeOutput
            // 
            this.DrivingModeOutput.AutoSize = true;
            this.DrivingModeOutput.Location = new System.Drawing.Point(268, 99);
            this.DrivingModeOutput.Name = "DrivingModeOutput";
            this.DrivingModeOutput.Size = new System.Drawing.Size(29, 12);
            this.DrivingModeOutput.TabIndex = 31;
            this.DrivingModeOutput.Text = "중립";
            // 
            // EmergencyOutput
            // 
            this.EmergencyOutput.AutoSize = true;
            this.EmergencyOutput.Location = new System.Drawing.Point(255, 123);
            this.EmergencyOutput.Name = "EmergencyOutput";
            this.EmergencyOutput.Size = new System.Drawing.Size(28, 12);
            this.EmergencyOutput.TabIndex = 32;
            this.EmergencyOutput.Text = "OFF";
            // 
            // btnStart
            // 
            this.btnStart.Location = new System.Drawing.Point(341, 179);
            this.btnStart.Name = "btnStart";
            this.btnStart.Size = new System.Drawing.Size(75, 45);
            this.btnStart.TabIndex = 33;
            this.btnStart.Text = "Server Start";
            this.btnStart.UseVisualStyleBackColor = true;
            this.btnStart.Click += new System.EventHandler(this.btnStart_Click);
            // 
            // DriveOutput
            // 
            this.DriveOutput.AutoSize = true;
            this.DriveOutput.Location = new System.Drawing.Point(183, 190);
            this.DriveOutput.Name = "DriveOutput";
            this.DriveOutput.Size = new System.Drawing.Size(21, 12);
            this.DriveOutput.TabIndex = 34;
            this.DriveOutput.Text = "0%";
            // 
            // Output
            // 
            this.Output.AutoSize = true;
            this.Output.Location = new System.Drawing.Point(210, 190);
            this.Output.Name = "Output";
            this.Output.Size = new System.Drawing.Size(81, 12);
            this.Output.TabIndex = 35;
            this.Output.Text = "Brake CMD : ";
            // 
            // BrakeOutput
            // 
            this.BrakeOutput.AutoSize = true;
            this.BrakeOutput.Location = new System.Drawing.Point(289, 190);
            this.BrakeOutput.Name = "BrakeOutput";
            this.BrakeOutput.Size = new System.Drawing.Size(21, 12);
            this.BrakeOutput.TabIndex = 36;
            this.BrakeOutput.Text = "0%";
            // 
            // Right_Wheel3
            // 
            this.Right_Wheel3.AutoSize = true;
            this.Right_Wheel3.Location = new System.Drawing.Point(545, 92);
            this.Right_Wheel3.Name = "Right_Wheel3";
            this.Right_Wheel3.Size = new System.Drawing.Size(33, 12);
            this.Right_Wheel3.TabIndex = 37;
            this.Right_Wheel3.Text = "0rpm";
            // 
            // Right_Wheel2
            // 
            this.Right_Wheel2.AutoSize = true;
            this.Right_Wheel2.Location = new System.Drawing.Point(545, 69);
            this.Right_Wheel2.Name = "Right_Wheel2";
            this.Right_Wheel2.Size = new System.Drawing.Size(33, 12);
            this.Right_Wheel2.TabIndex = 38;
            this.Right_Wheel2.Text = "0rpm";
            // 
            // Right_Wheel1
            // 
            this.Right_Wheel1.AutoSize = true;
            this.Right_Wheel1.Location = new System.Drawing.Point(545, 42);
            this.Right_Wheel1.Name = "Right_Wheel1";
            this.Right_Wheel1.Size = new System.Drawing.Size(33, 12);
            this.Right_Wheel1.TabIndex = 39;
            this.Right_Wheel1.Text = "0rpm";
            // 
            // set_rpm_timer
            // 
            this.set_rpm_timer.Tick += new System.EventHandler(this.set_rpm_timer_Tick);
            // 
            // btnVisualizerStart
            // 
            this.btnVisualizerStart.Location = new System.Drawing.Point(434, 179);
            this.btnVisualizerStart.Name = "btnVisualizerStart";
            this.btnVisualizerStart.Size = new System.Drawing.Size(77, 45);
            this.btnVisualizerStart.TabIndex = 40;
            this.btnVisualizerStart.Text = "Visualizer Server Start";
            this.btnVisualizerStart.UseVisualStyleBackColor = true;
            this.btnVisualizerStart.Click += new System.EventHandler(this.btnVisualizerStart_Click);
            // 
            // VisualizerTimer
            // 
            this.VisualizerTimer.Interval = 1000;
            this.VisualizerTimer.Tick += new System.EventHandler(this.VisualizerTimer_Tick);
            // 
            // ReportTimer
            // 
            this.ReportTimer.Interval = 1000;
            this.ReportTimer.Tick += new System.EventHandler(this.ReportTimer_Tick);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(727, 236);
            this.Controls.Add(this.btnVisualizerStart);
            this.Controls.Add(this.Right_Wheel1);
            this.Controls.Add(this.Right_Wheel2);
            this.Controls.Add(this.Right_Wheel3);
            this.Controls.Add(this.BrakeOutput);
            this.Controls.Add(this.Output);
            this.Controls.Add(this.DriveOutput);
            this.Controls.Add(this.btnStart);
            this.Controls.Add(this.EmergencyOutput);
            this.Controls.Add(this.DrivingModeOutput);
            this.Controls.Add(this.OPModeOutput);
            this.Controls.Add(this.VeloOutput);
            this.Controls.Add(this.label19);
            this.Controls.Add(this.TCUStatus);
            this.Controls.Add(this.FCUStatus);
            this.Controls.Add(this.MCUStatus);
            this.Controls.Add(this.PCUStatus);
            this.Controls.Add(this.DCUStatus);
            this.Controls.Add(this.label18);
            this.Controls.Add(this.label17);
            this.Controls.Add(this.label16);
            this.Controls.Add(this.label15);
            this.Controls.Add(this.label14);
            this.Controls.Add(this.label13);
            this.Controls.Add(this.Left_Wheel3);
            this.Controls.Add(this.Left_Wheel2);
            this.Controls.Add(this.Left_Wheel1);
            this.Controls.Add(this.label9);
            this.Controls.Add(this.label8);
            this.Controls.Add(this.label7);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.SteerOutput);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.DUPcoolant);
            this.Controls.Add(this.DUPsoc);
            this.Controls.Add(this.DUPfuel);
            this.Controls.Add(this.dsa);
            this.Controls.Add(this.lab_SOC);
            this.Controls.Add(this.lab_fuel);
            this.Name = "Form1";
            this.Text = "Form1";
            this.Load += new System.EventHandler(this.Form1_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label lab_fuel;
        private System.Windows.Forms.Label lab_SOC;
        private System.Windows.Forms.Label dsa;
        private System.Windows.Forms.DomainUpDown DUPfuel;
        private System.Windows.Forms.DomainUpDown DUPsoc;
        private System.Windows.Forms.DomainUpDown DUPcoolant;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label SteerOutput;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.Label Left_Wheel1;
        private System.Windows.Forms.Label Left_Wheel2;
        private System.Windows.Forms.Label Left_Wheel3;
        private System.Windows.Forms.Label label13;
        private System.Windows.Forms.Label label14;
        private System.Windows.Forms.Label label15;
        private System.Windows.Forms.Label label16;
        private System.Windows.Forms.Label label17;
        private System.Windows.Forms.Label label18;
        private System.Windows.Forms.ComboBox DCUStatus;
        private System.Windows.Forms.ComboBox PCUStatus;
        private System.Windows.Forms.ComboBox MCUStatus;
        private System.Windows.Forms.ComboBox FCUStatus;
        private System.Windows.Forms.ComboBox TCUStatus;
        private System.Windows.Forms.Label label19;
        private System.Windows.Forms.Label VeloOutput;
        private System.Windows.Forms.Label OPModeOutput;
        private System.Windows.Forms.Label DrivingModeOutput;
        private System.Windows.Forms.Label EmergencyOutput;
        private System.Windows.Forms.Button btnStart;
        private System.Windows.Forms.Label DriveOutput;
        private System.Windows.Forms.Label Output;
        private System.Windows.Forms.Label BrakeOutput;
        private System.Windows.Forms.Label Right_Wheel3;
        private System.Windows.Forms.Label Right_Wheel2;
        private System.Windows.Forms.Label Right_Wheel1;
        private System.Windows.Forms.Timer set_rpm_timer;
        private System.Windows.Forms.Button btnVisualizerStart;
        private System.Windows.Forms.Timer VisualizerTimer;
        private System.Windows.Forms.Timer ReportTimer;
    }
}

