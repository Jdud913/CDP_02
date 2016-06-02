package commie.com.example.septembernine.commie01.commie;

/**
 * Created by septembernine on 2016. 4. 3..
 */
public class Commie_Beacon02  {
//    private static final String TAG = "DD";
//
//    private RECOBeaconManager recoManager;
//    private ArrayList<RECOBeaconRegion> rangingRegions;
//
//    List<String> uid;
//    List<String> major;
//    List<String> minor;
//
//
//    public static final String RECO_UUID = "24DDF411-8CF1-440C-87CD-E368DAF9C93E";
//    public static final boolean SCAN_RECO_ONLY = true;
//    public static final boolean ENABLE_BACKGROUND_RANGING_TIMEOUT = true;
//    public static final boolean DISCONTINUOUS_SCAN = false;
//
//
//    double[] Accuracy = {9999,9999,9999,9999,9999,9999,9999,9999,9999,9999,9999,9999,9999};
//    int[] rssi_b = {9999,9999,9999,9999,9999,9999,9999,9999,9999,9999,9999,9999,9999};
//    int[] tx_Power = {9999,9999,9999,9999,9999,9999,9999,9999,9999,9999,9999,9999,9999};
//
//    int[] check_index = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
//    int[] On_or_Off = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
//    int index = -1;
//    double temp;
//
//    String resName;
//    String packName;
//    int resID;
//
//    private String html = "";
//
//    TextHandler handler;
//    private Socket socket;
//    private BufferedReader networkReader;
//    MainThread thread;
//    OutputStream out  ;
//    DataOutputStream dos  ;
//    String id;
//
//    int t = 1;
//    private String ip = "20.20.3.154"; // IP
//    private int port = 7777; // PORT번호
//    private GpsInfo gps;
//    ////////////////////////////////////////////////////////////////////////////////////
//    private static final int REQUEST_ENABLE_BT = 1;
//    private static final int REQUEST_LOCATION = 10;
//
//    private BluetoothManager mBluetoothManager;
//    private BluetoothAdapter mBluetoothAdapter;
//
//    private View mLayout;
//
//    private Button btnStart, btnEnd;
////////////////////////////////////////////////////////////////////////////////////////////
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_commie_beacon02);
//
////////////////////////////////////////////////////////////////////////////////////////////
//        Intent i = getIntent();
//        id = i.getExtras().getString("id_u");
//
//        //////////
//        uid = new ArrayList<String>();
//        major = new ArrayList<String>();
//        minor = new ArrayList<String>();
//        //////////
//
//        handler = new TextHandler();
//        Log.d("sequen :" , "1");
//
//        if(thread != null && thread.isAlive())
//            thread.interrupt();
//        Log.d("sequen :" , "2");
//
//        thread = new MainThread();
//        thread.setDaemon(true);
//        thread.start();
//
//        Log.d("sequen :", "5");
//        packName = this.getPackageName();
//
//        //beacon감지 시작
//        recoManager = RECOBeaconManager.getInstance(this, true, true);
//        recoManager.bind(this);
//        recoManager.setRangingListener(this);
//        //////////////////////////////////////////////////////////////////////////////////////////
//
//
//        mLayout = findViewById(R.id.mainLayout);
//
//        btnStart = (Button) findViewById(R.id.bnt_start);
//        btnEnd = (Button) findViewById(R.id.bnt_End);
//
//        btnStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Service 시작", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(Commie_Beacon02.this, MyService.class);
//                startService(intent);
//            }
//        });
//        Log.w("1", "Start 1");
//
//        btnEnd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Service 끝", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(Commie_Beacon02.this, MyService.class);
//                stopService(intent);
//            }
//        });
//
//        //If a user device turns off bluetooth, request to turn it on.
//        //사용자가 블루투스를 켜도록 요청합니다.
//        mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
//        mBluetoothAdapter = mBluetoothManager.getAdapter();
//
//        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
//            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(enableBTIntent, REQUEST_ENABLE_BT);
//        }
//
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                Log.i("MainActivity", "The location permission (ACCESS_COARSE_LOCATION or ACCESS_FINE_LOCATION) is not granted.");
//                this.requestLocationPermission();
//            } else {
//                Log.i("MainActivity", "The location permission (ACCESS_COARSE_LOCATION or ACCESS_FINE_LOCATION) is already granted.");
//            }
//        }
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
//            //If the request to turn on bluetooth is denied, the app will be finished.
//            //사용자가 블루투스 요청을 허용하지 않았을 경우, 어플리케이션은 종료됩니다.
//            finish();
//            return;
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch(requestCode) {
//            case REQUEST_LOCATION : {
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Snackbar.make(mLayout, R.string.location_permission_granted, Snackbar.LENGTH_LONG).show();
//                } else {
//                    Snackbar.make(mLayout, R.string.location_permission_not_granted, Snackbar.LENGTH_LONG).show();
//                }
//            }
//            default :
//                break;
//        }
//
//
//    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        if(this.isBackgroundMonitoringServiceRunning(this)) {
//            ToggleButton toggle = (ToggleButton)findViewById(R.id.backgroundMonitoringToggleButton);
//            toggle.setChecked(true);
//        }
//
//        if(this.isBackgroundRangingServiceRunning(this)) {
//            ToggleButton toggle = (ToggleButton)findViewById(R.id.backgroundRangingToggleButton);
//            toggle.setChecked(true);
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }
//
//    /**
//     *
//     * 안드로이드 API 23 (마시멜로우)이상 버전부터, 정상적으로 RECO SDK를 사용하기 위해서는
//     * 위치 권한 (ACCESS_COARSE_LOCATION 혹은 ACCESS_FINE_LOCATION)을 요청해야 합니다.
//     * 본 샘플 프로젝트에서는 "ACCESS_COARSE_LOCATION"을 요청하지만,
//     * 필요에 따라 "ACCESS_FINE_LOCATION"을 요청할 수 있습니다.
//     * 당사에서는 ACCESS_COARSE_LOCATION 권한을 권장합니다.
//     *
//     */
//    private void requestLocationPermission() {
//        if(!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
//            return;
//        }
//
//        Snackbar.make(mLayout, R.string.location_permission_rationale, Snackbar.LENGTH_INDEFINITE)
//                .setAction(R.string.ok, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ActivityCompat.requestPermissions(Commie_Beacon02.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
//                    }
//                })
//                .show();
//    }
//
//    public void onMonitoringToggleButtonClicked(View v) {
//        ToggleButton toggle = (ToggleButton)v;
//        if(toggle.isChecked()) {
//            Log.i("MainActivity", "onMonitoringToggleButtonClicked off to on");
//            Intent intent = new Intent(this, RecoBackgroundMonitoringService.class);
//            startService(intent);
//        } else {
//            Log.i("MainActivity", "onMonitoringToggleButtonClicked on to off");
//            stopService(new Intent(this, RecoBackgroundMonitoringService.class));
//        }
//    }
//
//    public void onRangingToggleButtonClicked(View v) {
//        ToggleButton toggle = (ToggleButton)v;
//        if(toggle.isChecked()) {
//            Log.i("MainActivity", "onRangingToggleButtonClicked off to on");
//            Intent intent = new Intent(this, RecoBackgroundRangingService.class);
//            startService(intent);
//        } else {
//            Log.i("MainActivity", "onRangingToggleButtonClicked on to off");
//            stopService(new Intent(this, RecoBackgroundRangingService.class));
//        }
//    }
//
//    public void onButtonClicked(View v) {
//        Button btn = (Button)v;
//        if(btn.getId() == R.id.monitoringButton) {
//            final Intent intent = new Intent(this, RecoMonitoringActivity.class);
//            startActivity(intent);
//        } else {
//            final Intent intent = new Intent(this, RecoRangingActivity.class);
//            startActivity(intent);
//        }
//    }
//
//    private boolean isBackgroundMonitoringServiceRunning(Context context) {
//        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
//        for(ActivityManager.RunningServiceInfo runningService : am.getRunningServices(Integer.MAX_VALUE)) {
//            if(RecoBackgroundMonitoringService.class.getName().equals(runningService.service.getClassName())) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private boolean isBackgroundRangingServiceRunning(Context context) {
//        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
//        for(ActivityManager.RunningServiceInfo runningService : am.getRunningServices(Integer.MAX_VALUE)) {
//            if(RecoBackgroundRangingService.class.getName().equals(runningService.service.getClassName())) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public void onServiceConnect() {
////  리스트 생성해서 비콘 값 저장
//        rangingRegions = new ArrayList<RECOBeaconRegion>();
////  비콘의UUID, Major, 이름
//        try{
//            Thread.sleep(1000);
//        }catch (InterruptedException e){
//
//        }
//        for(int i=0; i<uid.size(); i++) {
//            rangingRegions.add(new RECOBeaconRegion(uid.get(i), Integer.parseInt(major.get(i)), Integer.parseInt(minor.get(i)), Integer.toString(i)));
//
//        }
////        rangingRegions.add(new RECOBeaconRegion("24DDF411-8CF1-440C-87CD-E368DAF9C93E", 501, 1979, "0"));
////        rangingRegions.add(new RECOBeaconRegion("24DDF411-8CF1-440C-87CD-E368DAF9C93E", 501, 1984, "1"));
////        rangingRegions.add(new RECOBeaconRegion("24DDF411-8CF1-440C-87CD-E368DAF9C93E", 501, 1987, "2"));
////        rangingRegions.add(new RECOBeaconRegion("24DDF411-8CF1-440C-87CD-E368DAF9C93E", 501, 1995, "3"));
//
//
//        for (RECOBeaconRegion region : rangingRegions) {
//            try {
//                recoManager.startRangingBeaconsInRegion(region);
//                recoManager.requestStateForRegion(region);
//            } catch (RemoteException e) {
//                // RemoteException 발생 시 작성 코드
//            } catch (NullPointerException e) {
//                // NullPointerException 발생 시 작성 코드
//            }
//        }
//    }
//
//
//    @Override
//    public void onServiceFail(RECOErrorCode arg0) {
//        // TODO Auto-generated method stub
//    }

}
