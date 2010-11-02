package com.lokling.chr6dm;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CHR6DM {

    // Null packet
    public static final int NO_DATA                   = 0x00;
    public static final int FAILED_CHECKSUM           = 0x01;

    public static final int[] NO_DATA_PACKET          = new int[]{NO_DATA};
    public static final int[] FAILED_CHECKSUM_PACKET  = new int[]{FAILED_CHECKSUM};

    // Client command packets
    public static final int SET_ACTIVE_CHANNELS       =  0x80;
    public static final int SET_SILENT_MODE           =  0x81;
    public static final int SET_BROADCAST_MODE        =  0x82;
    public static final int SET_GYRO_BIAS             =  0x83;
    public static final int SET_ACCEL_BIAS            =  0x84;
    public static final int SET_ACCEL_REF_VECTOR      =  0x85;
    public static final int AUTO_SET_ACCEL_REF        =  0x86;
    public static final int ZERO_RATE_GYROS           =  0x87;
    public static final int SELF_TEST                 =  0x88;
    public static final int SET_START_CAL             =  0x89;
    public static final int SET_PROCESS_COVARIANCE    =  0x8A;
    public static final int SET_MAG_COVARIANCE        =  0x8B;
    public static final int SET_ACCEL_COVARIANCE      =  0x8C;
    public static final int SET_EKF_CONFIG            =  0x8D;
    public static final int SET_GYRO_ALIGNMENT        =  0x8E;
    public static final int SET_ACCEL_ALIGNMENT       =  0x8F;
    public static final int SET_MAG_REF_VECTOR        =  0x90;
    public static final int AUTO_SET_MAG_REF          =  0x91;
    public static final int SET_MAG_CAL               =  0x92;
    public static final int SET_MAG_BIAS              =  0x93;
    public static final int SET_GYRO_SCALE            =  0x94;
    public static final int EKF_RESET                 =  0x95;
    public static final int RESET_TO_FACTORY          =  0x96;
    public static final int WRITE_TO_FLASH            =  0xA0;
    public static final int GET_DATA                  =  0x01;
    public static final int GET_ACTIVE_CHANNELS       =  0x02;
    public static final int GET_BROADCAST_MODE        =  0x03;
    public static final int GET_ACCEL_BIAS            =  0x04;
    public static final int GET_ACCEL_REF_VECTOR      =  0x05;
    public static final int GET_GYRO_BIAS             =  0x06;
    public static final int GET_GYRO_SCALE            =  0x07;
    public static final int GET_START_CAL             =  0x08;
    public static final int GET_EKF_CONFIG            =  0x09;
    public static final int GET_ACCEL_COVARIANCE      =  0x0A;
    public static final int GET_MAG_COVARIANCE        =  0x0B;
    public static final int GET_PROCESS_COVARIANCE    =  0x0C;
    public static final int GET_STATE_COVARIANCE      =  0x0D;
    public static final int GET_GYRO_ALIGNMENT        =  0x0E;
    public static final int GET_ACCEL_ALIGNMENT       =  0x0F;
    public static final int GET_MAG_REF_VECTOR        =  0x10;
    public static final int GET_MAG_CAL               =  0x11;
    public static final int GET_MAG_BIAS              =  0x12;

    // Board status and data packets
    public static final int COMMAND_COMPLETE          =  0xB0;
    public static final int COMMAND_FAILED            =  0xB1;
    public static final int BAD_CHECKSUM              =  0xB2;
    public static final int BAD_DATA_LENGTH           =  0xB3;
    public static final int UNRECOGNIZED_PACKET       =  0xB4;
    public static final int BUFFER_OVERFLOW           =  0xB5;
    public static final int STATUS_REPORT             =  0xB6;
    public static final int SENSOR_DATA               =  0xB7;
    public static final int GYRO_BIAS_REPORT          =  0xB8;
    public static final int GYRO_SCALE_REPORT         =  0xB9;
    public static final int START_CAL_REPORT          =  0xBA;
    public static final int ACCEL_BIAS_REPORT         =  0xBB;
    public static final int ACCEL_REF_VECTOR_REPORT   =  0xBC;
    public static final int ACTIVE_CHANNEL_REPORT     =  0xBD;
    public static final int ACCEL_COVARIANCE_REPORT   =  0xBE;
    public static final int MAG_COVARIANCE_REPORT     =  0xBF;
    public static final int PROCESS_COVARIANCE_REPORT =  0xC0;
    public static final int STATE_COVARIANCE_REPORT   =  0xC1;
    public static final int EKF_CONFIG_REPORT         =  0xC2;
    public static final int GYRO_ALIGNMENT_REPORT     =  0xC3;
    public static final int ACCEL_ALIGNMENT_REPORT    =  0xC4;
    public static final int MAG_REF_VECTOR_REPORT     =  0xC5;
    public static final int MAG_CAL_REPORT            =  0xC6;
    public static final int MAG_BIAS_REPORT           =  0xC7;
    public static final int BROADCAST_MODE_REPORT     =  0xC8;

    private static final int[] PACKET_HEADER = new int[]{'s','n','p'};
    private static final int HEADER_CHECKSUM = 's'+'n'+'p';

    public static final int CHANNEL_YAW_MASK           = 1<<15;
    public static final int CHANNEL_PITCH_MASK         = 1<<14;
    public static final int CHANNEL_ROLL_MASK          = 1<<13;
    public static final int CHANNEL_YAW_RATE_MASK      = 1<<12;
    public static final int CHANNEL_PITCH_RATE_MASK    = 1<<11;
    public static final int CHANNEL_ROLL_RATE_MASK     = 1<<10;
    public static final int CHANNEL_MX_MASK            = 1<<9;
    public static final int CHANNEL_MY_MASK            = 1<<8;
    public static final int CHANNEL_MZ_MASK            = 1<<7;
    public static final int CHANNEL_GX_MASK            = 1<<6;
    public static final int CHANNEL_GY_MASK            = 1<<5;
    public static final int CHANNEL_GZ_MASK            = 1<<4;
    public static final int CHANNEL_AY_MASK            = 1<<3;
    public static final int CHANNEL_AX_MASK            = 1<<2;
    public static final int CHANNEL_AZ_MASK            = 1<<1;
    public static final int CHANNEL_ALL_MASK           = 65535;


    // Scale factors
    public static double SCALE_YAW        = 0.0109863; // °/LSB
    public static double SCALE_PITCH      = 0.0109863;
    public static double SCALE_ROLL       = 0.0109863;
    public static double SCALE_YAW_RATE   = 0.0137329; // °/s/LSB
    public static double SCALE_PITCH_RATE = 0.0137329;
    public static double SCALE_ROLL_RATE  = 0.0137329;
    public static double SCALE_MAG_X      = 0.0610350; // mGauss/LSB
    public static double SCALE_MAG_Y      = 0.0610350;
    public static double SCALE_MAG_Z      = 0.0610350;
    public static double SCALE_GYRO_X     = 0.0181200; // °/s/LSB
    public static double SCALE_GYRO_Y     = 0.0181200;
    public static double SCALE_GYRO_Z     = 0.0181200;
    public static double SCALE_ACCEL_X    = 0.1068120; // mg/LSB
    public static double SCALE_ACCEL_Y    = 0.1068120;
    public static double SCALE_ACCEL_Z    = 0.1068120;

    private final InputStream in;
    private final OutputStream out;


    public final Data data = new Data();

  public void EKFReset() {
    sendPacket(EKF_RESET);
    waitForAck(1000);
  }

    public void writeToFlash() {
        sendPacket(WRITE_TO_FLASH);
        waitForAck(5000);
    }

    public class Data{
        @Override
        public String toString() {
            return String.format("Data{ " +
                    "yaw=%.2f , pitch=%.2f, roll=%.2f, " +
                    "yawRate=%.2f, pitchRate=%.2f, rollRate=%.2f, " +
                    "mx=%.2f, my=%.2f, mz=%.2f, " +
                    "gx=%.2f, gy=%.2f, gz=%.2f, " +
                    "ax=%.2f, ay=%.2f, az=%.2f" +
                    "}",
                    yaw,pitch,roll,
                    yawRate,pitchRate,rollRate,
                    mx,my,mz,
                    gx,gy,gz,
                    az,ay,az
            );
        }

        public boolean yawEnabled;
        public boolean pitchEnabled;
        public boolean rollEnabled;
        public boolean yawRateEnabled;
        public boolean pitchRateEnabled;
        public boolean rollRateEnabled;
        public boolean mxEnabled;
        public boolean myEnabled;
        public boolean mzEnabled;
        public boolean gxEnabled;
        public boolean gyEnabled;
        public boolean gzEnabled;
        public boolean axEnabled;
        public boolean ayEnabled;
        public boolean azEnabled;

        public double yaw;
        public double pitch;
        public double roll;
        public double yawRate;
        public double pitchRate;
        public double rollRate;
        public double mx;
        public double my;
        public double mz;
        public double gx;
        public double gy;
        public double gz;
        public double ax;
        public double ay;
        public double az;
    }


    public CHR6DM(InputStream in, OutputStream out){
        this.in = in;
        this.out = out;
    }




    public int[] readPacket()  {
        try {

            if (!syncToHeader()){
                return NO_DATA_PACKET;
            }


            int packetType =  in.read() ;
            int dataBytes  =  in.read() ;

            int calculatedChecksum = HEADER_CHECKSUM + packetType + dataBytes;

            int[] packet = new int[dataBytes+1];
            packet[0] = packetType;

            for (int i = 1; i <= dataBytes ;i++ ){
                packet[i] = in.read() ;
                calculatedChecksum+=packet[i];
            }

            int high =  in.read();
            int low =   in.read();

            int packetChecksum = bytesToSignedShort(high,low);

            if (calculatedChecksum!=packetChecksum) {
                return FAILED_CHECKSUM_PACKET;
            }

            return packet;
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private boolean syncToHeader() throws IOException {
        while (in.available()>0){
            if (in.read()==PACKET_HEADER[0] && in.read()==PACKET_HEADER[1] && in.read()==PACKET_HEADER[2] ) return true;
        }

        return false;
    }


    public void resetToFactory()  {
        sendPacket(RESET_TO_FACTORY,new int[0]);
    }

    public void setActiveChannels(int channels)  {
        sendPacket(SET_ACTIVE_CHANNELS,new int[]{channels});
    }


    public void setBroadCastMode(int x) {
        sendPacket(SET_BROADCAST_MODE,new int[]{x});
    }

    private void sendPacket(int command)  {
        sendPacket(command,new int[]{});
    }

    private void sendPacket(int command, int[] bytes)  {
        try {
            int checksum = 0;
            int[] buffer = new int[]{'s','n','p',command,bytes.length};
            for (int i = 0; i < buffer.length; i++) {
                out.write(buffer[i]);
                checksum+=buffer[i];
            }

            for (int i = 0; i < bytes.length; i++) {
                out.write(bytes[i]);
                checksum+=bytes[i];

            }

            out.write(checksum>>8);
            out.write(checksum);

        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public boolean requestAndReadPacket() {
        sendPacket(GET_DATA);
        return waitFor(SENSOR_DATA, 1000);
    }


    private boolean waitFor(int command,int timeout) {
        long startTime = System.currentTimeMillis();
        while(System.currentTimeMillis()-startTime<timeout){
            int[] packet = readPacket();
            if (packet[0]==command){
                return decodePacket(packet);
            }
        }

        return false;
    }

    private boolean decodePacket(int[] packet) {
        int index = 0;
        switch (packet[index++]) {
            case SENSOR_DATA:

                int flags = bytesToSignedShort(packet[index++],packet[index++]);

                data.yawEnabled          = (flags & CHANNEL_YAW_MASK            ) == CHANNEL_YAW_MASK;
                data.pitchEnabled        = (flags & CHANNEL_PITCH_MASK          ) == CHANNEL_PITCH_MASK;
                data.rollEnabled         = (flags & CHANNEL_ROLL_MASK           ) == CHANNEL_ROLL_MASK;
                data.yawRateEnabled      = (flags & CHANNEL_YAW_RATE_MASK       ) == CHANNEL_YAW_RATE_MASK;
                data.pitchRateEnabled    = (flags & CHANNEL_PITCH_RATE_MASK     ) == CHANNEL_PITCH_RATE_MASK;
                data.rollRateEnabled     = (flags & CHANNEL_ROLL_RATE_MASK      ) == CHANNEL_ROLL_RATE_MASK;
                data.mxEnabled           = (flags & CHANNEL_MX_MASK             ) == CHANNEL_MX_MASK;
                data.myEnabled           = (flags & CHANNEL_MY_MASK             ) == CHANNEL_MY_MASK;
                data.mzEnabled           = (flags & CHANNEL_MZ_MASK             ) == CHANNEL_MZ_MASK;
                data.gxEnabled           = (flags & CHANNEL_GX_MASK             ) == CHANNEL_GX_MASK;
                data.gyEnabled           = (flags & CHANNEL_GY_MASK             ) == CHANNEL_GY_MASK;
                data.gzEnabled           = (flags & CHANNEL_GZ_MASK             ) == CHANNEL_GZ_MASK;
                data.axEnabled           = (flags & CHANNEL_AX_MASK             ) == CHANNEL_AX_MASK;
                data.ayEnabled           = (flags & CHANNEL_AY_MASK             ) == CHANNEL_AY_MASK;
                data.azEnabled           = (flags & CHANNEL_AZ_MASK             ) == CHANNEL_AZ_MASK;


                if (data.yawEnabled          ){ data.yaw          = SCALE_YAW           * bytesToSignedShort(packet[index++],packet[index++]); }
                if (data.pitchEnabled        ){ data.pitch        = SCALE_PITCH         * bytesToSignedShort(packet[index++],packet[index++]); }
                if (data.rollEnabled         ){ data.roll         = SCALE_ROLL          * bytesToSignedShort(packet[index++],packet[index++]); }
                if (data.yawRateEnabled      ){ data.yawRate      = SCALE_YAW_RATE      * bytesToSignedShort(packet[index++],packet[index++]); }
                if (data.pitchRateEnabled    ){ data.pitchRate    = SCALE_PITCH_RATE    * bytesToSignedShort(packet[index++],packet[index++]); }
                if (data.rollRateEnabled     ){ data.rollRate     = SCALE_ROLL_RATE     * bytesToSignedShort(packet[index++],packet[index++]); }
                if (data.mxEnabled           ){ data.mx           = SCALE_MAG_X         * bytesToSignedShort(packet[index++],packet[index++]); }
                if (data.myEnabled           ){ data.my           = SCALE_MAG_Y         * bytesToSignedShort(packet[index++],packet[index++]); }
                if (data.mzEnabled           ){ data.mz           = SCALE_MAG_Z         * bytesToSignedShort(packet[index++],packet[index++]); }
                if (data.gxEnabled           ){ data.gx           = SCALE_GYRO_X        * bytesToSignedShort(packet[index++],packet[index++]); }
                if (data.gyEnabled           ){ data.gy           = SCALE_GYRO_Y        * bytesToSignedShort(packet[index++],packet[index++]); }
                if (data.gzEnabled           ){ data.gz           = SCALE_GYRO_Z        * bytesToSignedShort(packet[index++],packet[index++]); }
                if (data.axEnabled           ){ data.ax           = SCALE_ACCEL_X       * bytesToSignedShort(packet[index++],packet[index++]); }
                if (data.ayEnabled           ){ data.ay           = SCALE_ACCEL_Y       * bytesToSignedShort(packet[index++],packet[index++]); }
                if (data.azEnabled           ){ data.az           = SCALE_ACCEL_Z       * bytesToSignedShort(packet[index++],packet[index++]); }

                if (index!=packet.length){
                    throw new RuntimeException("Error! Packet length and flag mismatch!");
                }

                return true;
            case STATUS_REPORT:
                return true;
            default:
                return false;

        }
    }


    public boolean selfTest(){
        sendPacket(SELF_TEST);
        return waitFor(STATUS_REPORT,1000);
    }

    private int bytesToSignedShort(int high, int low) {
        return (short)((high & 0xFF) << 8) | (low & 0xFF);
    }

    public void setListenMode() throws IOException {
        sendPacket(SET_SILENT_MODE);
        if (waitForAck(1000)){
            System.out.println("Set to listen mode");
        } else {
            System.out.println("Failed! to set listen mode");
        }
    }

    private boolean waitForAck(int timeout) {

        long startTime = System.currentTimeMillis();
        while(System.currentTimeMillis()-startTime<timeout){
            switch(readPacket()[0]){
                case COMMAND_COMPLETE :
                    return true;
                case COMMAND_FAILED:
                    return false;
                default:
                    System.out.println("skipping unexpected packet type in wait for ack.");
            }
        }

        return false;
    }
}
