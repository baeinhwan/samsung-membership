using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace systemProcessModule
{
    [Serializable]
    class Hpacket
    {
        public byte[] ID = new byte[4];
        public byte[] Length = new byte[4];

        public const byte EmergencyStop = 0x11;
        public const byte OPERATION_MODE = 0x21;
        public const byte DRIVING_MODE = 0x31;
        public const byte CRUISE_CONTROL = 0x46;



    }
}
