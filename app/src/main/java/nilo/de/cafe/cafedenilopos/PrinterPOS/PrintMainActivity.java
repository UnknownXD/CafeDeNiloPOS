package nilo.de.cafe.cafedenilopos.PrinterPOS;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.UUID;

import es.dmoral.toasty.Toasty;
import nilo.de.cafe.cafedenilopos.CafeDeNilo.ActivityPayment;
import nilo.de.cafe.cafedenilopos.CafeDeNilo.MainActivity;
import nilo.de.cafe.cafedenilopos.R;
import nilo.de.cafe.cafedenilopos.helper.SharedPrefManager;
import nilo.de.cafe.cafedenilopos.pos.PosActivity;

public class PrintMainActivity extends Activity{
    private String TAG = "Main Activity";
    EditText message;
    Button btnPrint, btnBill, btnDonate;
    int x = 0;

    byte FONT_TYPE;
    private static BluetoothSocket btsocket;
    private static OutputStream outputStream;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_main);
        btnPrint = (Button)findViewById(R.id.btnPrint);
        btnBill = (Button)findViewById(R.id.btnBill);

        device2();
        if (btsocket == null) {
            device2();
        } else {
            printBill();
        }
        btnPrint.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrintMainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnBill.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                printBill();
            }
        });

    }

    protected void printBill() {
        if(btsocket == null){
            Intent BTIntent = new Intent(getApplicationContext(), DeviceList.class);
            this.startActivityForResult(BTIntent, DeviceList.REQUEST_CONNECT_BT);
        }
        else{
            OutputStream opstream = null;
            try {
                opstream = btsocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            outputStream = opstream;

            //print command
            try {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String name = SharedPrefManager.getInstance(this).getUser().getFirst_name()+", "+SharedPrefManager.getInstance(this).getUser().getLast_name() ;
                printCustom("Cafe De Nilo",2,1);
                printCustom(".",0,1);
                printCustom("Taguig, 1630 Metro Manila, PAGASA",0,1);
                printCustom("Hot Line: +639453209236",0,1);
                printCustom("Vat Reg : 12%",0,1);
                String dateTime[] = getDateTime();
                printText(leftRightAlign(dateTime[0], dateTime[1]));
                printNewLine();
                printCustom(new String(new char[40]).replace("\0", "."),0,1);
                printText(leftRightAlign("Cashier:", name.toUpperCase()));
                printNewLine();
                printCustom(new String(new char[40]).replace("\0", "."),0,1);
                for (int i = 0; i < PosActivity.listName.size(); i++) {
                    printText(leftRightAlign(PosActivity.listQuantity.get(i)+" X "+ PosActivity.listName.get(i) , PosActivity.listPrice.get(i)));
                    printNewLine();
                }

                printCustom(new String(new char[40]).replace("\0", "."),0,1);
                printText(leftRightAlign("Sub Total:" , PosActivity.sumunvatted+""));
                printNewLine();
                printText(leftRightAlign("Vat:" , (PosActivity.sumunvatted * .12)+""));
                printNewLine();
                printText(leftRightAlign("Discount:" , (ActivityPayment.discount)+""));
                printNewLine();
                printText(leftRightAlign("Total:" , (ActivityPayment.payment)+""));
                printNewLine();
                printNewLine();
                printCustom("Thank you for coming & we look",0,1);
                printCustom("forward to serve you again",0,1);
                printNewLine();
                printNewLine();
                printNewLine();
                printNewLine();


                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void printDemo() {
        if(btsocket == null){
            Intent BTIntent = new Intent(getApplicationContext(), DeviceList.class);
            this.startActivityForResult(BTIntent, DeviceList.REQUEST_CONNECT_BT);
        }
        else{
            OutputStream opstream = null;
            try {
                opstream = btsocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            outputStream = opstream;

            //print command
            try {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                outputStream = btsocket.getOutputStream();

                byte[] printformat = { 0x1B, 0*21, FONT_TYPE };
                //outputStream.write(printformat);

                //print title
                printUnicode();
                //print normal text
                printCustom(message.getText().toString(),0,0);

                printNewLine();
                printText("     >>>>   Thank you  <<<<     "); // total 32 char in a single line
                //resetPrint(); //reset printer
                printUnicode();
                printNewLine();
                printNewLine();

                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //print custom
    private void printCustom(String msg, int size, int align) {
        //Print config "mode"
        byte[] cc = new byte[]{0x1B,0x21,0x03};  // 0- normal size text
        //byte[] cc1 = new byte[]{0x1B,0x21,0x00};  // 0- normal size text
        byte[] bb = new byte[]{0x1B,0x21,0x08};  // 1- only bold text
        byte[] bb2 = new byte[]{0x1B,0x21,0x20}; // 2- bold with medium text
        byte[] bb3 = new byte[]{0x1B,0x21,0x10}; // 3- bold with large text
        try {
            switch (size){
                case 0:
                    outputStream.write(cc);
                    break;
                case 1:
                    outputStream.write(bb);
                    break;
                case 2:
                    outputStream.write(bb2);
                    break;
                case 3:
                    outputStream.write(bb3);
                    break;
            }

            switch (align){
                case 0:
                    //left align
                    outputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
                    break;
                case 1:
                    //center align
                    outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                    break;
                case 2:
                    //right align
                    outputStream.write(PrinterCommands.ESC_ALIGN_RIGHT);
                    break;
            }
            outputStream.write(msg.getBytes());
            outputStream.write(PrinterCommands.LF);
            //outputStream.write(cc);
            //printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //print photo
    public void printPhoto(int img) {
        try {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                    img);
            if(bmp!=null){
                byte[] command = Utils.decodeBitmap(bmp);
                outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                printText(command);
            }else{
                Log.e("Print Photo error", "the file isn't exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PrintTools", "the file isn't exists");
        }
    }

    //print unicode
    public void printUnicode(){
        try {
            outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
            printText(Utils.UNICODE_TEXT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


        //print new line
        private void printNewLine() {
            try {
                outputStream.write(PrinterCommands.FEED_LINE);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    public static void resetPrint() {
        try{
            outputStream.write(PrinterCommands.ESC_FONT_COLOR_DEFAULT);
            outputStream.write(PrinterCommands.FS_FONT_ALIGN);
            outputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
            outputStream.write(PrinterCommands.ESC_CANCEL_BOLD);
            outputStream.write(PrinterCommands.LF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //print text
    private void printText(String msg) {
        try {
            // Print normal text
            outputStream.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //print byte[]
    private void printText(byte[] msg) {
        try {
            // Print normal text
            outputStream.write(msg);
            printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String leftRightAlign(String str1, String str2) {
        String ans = str1 +str2;
        if(ans.length() <40){
            int val = 40 - ans.length();
            int n = ((30 - str1.length() + str2.length()));
            ans = str1 + new String(new char[val]).replace("\0", " ") + str2;
           /* int n = ((28 - str1.length() + str2.length()));
            int value = 42 -(ans.length()+(n+str2.length()));
            ans = str1 + new String(new char[n+value]).replace("\0", "+") + str2;*/
        }
        return ans;
    }


    private String[] getDateTime() {
        Calendar c = Calendar.getInstance();
        String[] dateTime = new String[2];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(c.get(2) + 1);
        stringBuilder.append("/");
        stringBuilder.append(c.get(5));
        stringBuilder.append("/");
        stringBuilder.append(c.get(1));
        dateTime[0] = stringBuilder.toString();
        StringBuilder stringBuilder2;
        if (c.get(9) == 0) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(c.get(10));
            stringBuilder2.append(":");
            stringBuilder2.append(c.get(12));
            stringBuilder2.append("AM");
            dateTime[1] = stringBuilder2.toString();
        } else {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(c.get(10));
            stringBuilder2.append(":");
            stringBuilder2.append(c.get(12));
            stringBuilder2.append("PM");
            dateTime[1] = stringBuilder2.toString();
        }
        return dateTime;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if(btsocket!= null){
                outputStream.close();
                btsocket.close();
                btsocket = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            btsocket = DeviceList.getSocket();
            if(btsocket != null){
                printText(message.getText().toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void device2() {
        try {
            BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
            if (btAdapter.isEnabled()) {
                String btdevaddr = "66:12:A8:47:1F:5D";
                if (btdevaddr != "?") {
                    BluetoothDevice device = btAdapter.getRemoteDevice(btdevaddr);
                    try {
                        btsocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                    } catch (Exception e) {
                        Log.e("shit1", "Error creating socket");
                    }
                    try {
                        btsocket.connect();
                        Log.e("", "Connected");
                        return;
                    } catch (IOException e2) {
                        Log.e("shit1.5", e2.getMessage());
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("shit2");
                        stringBuilder.append(e2.getMessage());
                        Toasty.error((Context) this, stringBuilder.toString(), 0, true).show();
                        try {
                            Log.e("", "trying fallback...");
                            btsocket = (BluetoothSocket) device.getClass().getMethod("createRfcommSocket", new Class[]{Integer.TYPE}).invoke(device, new Object[]{Integer.valueOf(1)});
                            btsocket.connect();
                            Log.e("", "Connected");
                            return;
                        } catch (Exception e3) {
                            Log.e("shit3.5", "error");
                            Toasty.error((Context) this, (CharSequence) "Couldn't establish Bluetooth connection!", 0, true).show();
                            return;
                        }
                    }
                }
                Log.e("shit4", "BT device not selected");
            }
        } catch (Exception e4) {
            Toasty.error((Context) this, (CharSequence) "Couldn't establish Bluetooth connection!", 0, true).show();
        }
    }
}