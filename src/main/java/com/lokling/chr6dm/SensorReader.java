package com.lokling.chr6dm;

import gnu.io.*;

import java.io.IOException;
import java.util.Arrays;

public class SensorReader {

    public static void main(String[] pArgs) throws NoSuchPortException, UnsupportedCommOperationException, IOException, PortInUseException {


        String portName = "/dev/cu.SLAB_USBtoUART";

    CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
    if ( portIdentifier.isCurrentlyOwned() ){
      System.out.println("Error: Port is currently in use");
    } else {
      CommPort commPort = portIdentifier.open("CHR6 SensorReader",2000);

      if ( commPort instanceof SerialPort) {
        SerialPort serialPort = (SerialPort) commPort;

        serialPort.setSerialPortParams(115200,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);


          CHR6DM chr6DM = new CHR6DM(serialPort.getInputStream(),serialPort.getOutputStream());

          //chr6DM.selfTest();
          //chr6DM.resetToFactory();
          chr6DM.EKFReset();
          //chr6DM.writeToFlash();



          //readBroadcastPackets(chr6DM);
          silentModeReads(chr6DM);



      } else {
        System.out.println("Error: Only serial ports are handled by this example.");
      }
    }








    }

    private static void silentModeReads(CHR6DM chr6DM) throws IOException {
        //chr6DM.resetToFactory();
        chr6DM.setListenMode();
        chr6DM.setActiveChannels(CHR6DM.CHANNEL_YAW_MASK );

        int received = 0;
        long lastCheck = System.currentTimeMillis();
        while(true){
            if (chr6DM.requestAndReadPacket()){
                //System.out.println(chr6DM.data);
                received++;
            } else {
                System.out.println("Error in read!");
            }
            final long currentTime = System.currentTimeMillis();
            final long timepassed = currentTime - lastCheck;
            if (timepassed > 1000){
                System.out.println(chr6DM.data);
                System.out.println(String.format("Reveived %s packets %shz",received,((float)received*1000)/(float)timepassed));

                lastCheck = currentTime;
                received=0;

            }
        }
    }


    

    private static void readBroadcastPackets(CHR6DM chr6DM) {

          chr6DM.resetToFactory();
          chr6DM.setBroadCastMode(254);
          chr6DM.setActiveChannels(CHR6DM.CHANNEL_ALL_MASK);


        int received = 0;
        long lastCheck = System.currentTimeMillis();
        while(true){

          final int[] packet = chr6DM.readPacket();

          switch (packet[0]){
              case CHR6DM.FAILED_CHECKSUM:
                  System.out.println("Bad checksum!");
                  break;
              case CHR6DM.NO_DATA:
                  //System.out.println("No data read!");
                  break;
              default:
                  System.out.println(Arrays.toString(packet));
                  received++;
              }

          final long currentTime = System.currentTimeMillis();
          final long timepassed = currentTime - lastCheck;
          if (timepassed > 1000){
              System.out.println(String.format("Reveived %s packets %shz",received,((float)received*1000)/(float)timepassed));

              lastCheck = currentTime;
              received=0;

          }

      }
    }


}
