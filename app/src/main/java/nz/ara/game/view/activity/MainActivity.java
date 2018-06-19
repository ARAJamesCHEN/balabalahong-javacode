package nz.ara.game.view.activity;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.constraint.Guideline;
import android.util.Log;

import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.yac0105.game.R;
//import com.example.yac0105.game.databinding.ActivityMainBinding;

import java.io.File;

import nz.ara.game.model.em.constvalue.Const;
import nz.ara.game.view.adapter.ViewBindingAdapter;
import nz.ara.game.view.util.DisplayParams;
import nz.ara.game.view.util.DisplayUtil;
import nz.ara.game.view.views.MapView;
import nz.ara.game.view.views.RoleView;
import nz.ara.game.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    ConstraintSet constraintSet;

    private Spinner level_spinner;

    private MapView mapView;

    private RoleView theView;

    private RoleView minView;

    private TextView textViewName;

    private TextView textViewMoveCount;

    private Button reset;

    private Button pause;

    private Button save;

    private Button loadByFile;

    private Button help;

    private Button more;

    private String level_string = "Level-1";

    private MainViewModel mainViewModel;

    private Context context;

    ConstraintLayout constraintLayout;

    Guideline guideline1;


    Guideline guideline2;

   // private ActivityMainBinding binding;

    private int rolePointXShort = 100;

    private int rolePointXLong = 100;

    private int rolePointYShort = 200;

    private int rolePointYLong = 200;

    private float startX;

    private float startY;

    private File directory;

    private String fileP;

    private boolean isSaveSuccessful = false;

    private boolean isLoadSuccessful = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        if(mainViewModel == null){
            mainViewModel = new MainViewModel(this,level_string);
        }

        setContentView(R.layout.activity_main);


        DisplayParams displayParams = DisplayParams.getInstance(context);

        constraintLayout = findViewById(R.id.constraintLayout);

        ConstraintLayout.LayoutParams layoutParams;

        layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
        layoutParams.editorAbsoluteX =  DisplayUtil.dip2px(81, displayParams.scale);

        constraintLayout.setLayoutParams(layoutParams);

        setContentView(constraintLayout);
        //https://stackoverflow.com/questions/41670618/android-how-to-programatically-set-layout-constraintright-torightof-parent
        //https://juejin.im/entry/58b2fd59570c350069704265



        int dpToPix_8 = DisplayUtil.dip2px(8, displayParams.scale);

        //Move Count:
        textViewName = new TextView(this);
        textViewName.setId(R.id.textView_move_name);
        textViewName.setText(R.string.text_mvcount_name);

        layoutParams = new ConstraintLayout.LayoutParams(DisplayUtil.dip2px(85, displayParams.scale), DisplayUtil.dip2px(28, displayParams.scale));

        //layoutParams.setMargins(dpToPix_8,dpToPix_8,dpToPix_8,dpToPix_8);
        layoutParams.leftMargin = dpToPix_8;

        textViewName.setLayoutParams(layoutParams);
        constraintLayout.addView(textViewName);

        //moveCount
        textViewMoveCount = new TextView(this);
        textViewMoveCount.setId(R.id.textView2);


        textViewMoveCount.setText(mainViewModel.moveCount.get());
        layoutParams = new ConstraintLayout.LayoutParams(DisplayUtil.dip2px(113, displayParams.scale), DisplayUtil.dip2px(27, displayParams.scale));

        //layoutParams.setMargins(dpToPix_8,dpToPix_8,dpToPix_8,dpToPix_8);
        layoutParams.bottomMargin = dpToPix_8;
        layoutParams.leftMargin = dpToPix_8;
        layoutParams.topMargin = dpToPix_8;
        textViewName.setLayoutParams(layoutParams);
        constraintLayout.addView(textViewMoveCount);


        help  = new Button(this);
        help.setId(R.id.button_help);
        help.setText(R.string.button_help_name);
        layoutParams = new ConstraintLayout.LayoutParams(DisplayUtil.dip2px(97, displayParams.scale), DisplayUtil.dip2px(37, displayParams.scale));

        //layoutParams.setMargins(dpToPix_8,dpToPix_8,dpToPix_8,dpToPix_8);
        layoutParams.rightMargin = dpToPix_8;
        layoutParams.leftMargin = DisplayUtil.dip2px(64, displayParams.scale);

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpButtonClicked();
            }}
        );
        constraintLayout.addView(help);

        // FrameLayout
        FrameLayout f = new FrameLayout(this);
        f.setId(R.id.frameLayout);

        FrameLayout.LayoutParams fLayoutParames = new FrameLayout.LayoutParams(DisplayUtil.dip2px(368, displayParams.scale), DisplayUtil.dip2px(342, displayParams.scale));
        fLayoutParames.setMargins(dpToPix_8,dpToPix_8,dpToPix_8,dpToPix_8);
        fLayoutParames.bottomMargin = dpToPix_8;
        f.setLayoutParams(fLayoutParames);

        mapView = new MapView(this);
        mapView.setId(R.id.mapview);
        layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
        mapView.setLayoutParams(layoutParams);
        mapView.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        mapView.setItemsWallAboveStr(mainViewModel.wallAbovePointListStr.get());
        mapView.setItemsWallLeftStr(mainViewModel.wallLeftPointListStr.get());
        mapView.setWallSquareStr(mainViewModel.wallSquareStr.get());

        f.addView(mapView, 0);

        theView = new RoleView(this,getResources().getString(R.string.ROLE_TYPE_THESEUS));
        layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
        theView.setLayoutParams(layoutParams);
        theView.setBackgroundColor(Color.TRANSPARENT);
        theView.setHeightStr(mainViewModel.heightStr.get());
        theView.setPointStr(mainViewModel.thePointStr.get());
        theView.setWallSquareStr(mainViewModel.wallSquareStr.get());
        theView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return roleViewOnTouched(event);
            }
        });
        theView.bringToFront();
        f.addView(theView,1);


        minView = new RoleView(this,getResources().getString(R.string.ROLE_TYPE_MINOTAUR));
        layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
        minView.setLayoutParams(layoutParams);
        minView.setBackgroundColor(Color.TRANSPARENT);
        minView.setHeightStr(mainViewModel.heightStr.get());
        minView.setPointStr(mainViewModel.minPointStr.get());
        minView.setWallSquareStr(mainViewModel.wallSquareStr.get());
        f.addView(minView,2);


        constraintLayout.addView(f);

        level_spinner = new Spinner(this);
        level_spinner.setId(R.id.level_spinner);
        level_spinner.setPromptId(R.string.level_prompt);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, mainViewModel.getLevels() );
        level_spinner.setAdapter(spinnerArrayAdapter);

        layoutParams = new ConstraintLayout.LayoutParams(DisplayUtil.dip2px(136, displayParams.scale), DisplayUtil.dip2px(42, displayParams.scale));

        layoutParams.bottomMargin = dpToPix_8;
        layoutParams.leftMargin = dpToPix_8;
        layoutParams.topMargin = dpToPix_8;

        level_spinner.setLayoutParams(layoutParams);

        level_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                spinnerItemSelected();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });
        constraintLayout.addView(level_spinner);

        //reset
        reset = new Button(this);
        reset.setId(R.id.button_reset);
        reset.setText(R.string.button_reset_name);

        layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

        //layoutParams.setMargins(dpToPix_8,dpToPix_8,dpToPix_8,dpToPix_8);
        layoutParams.bottomMargin = dpToPix_8;
        layoutParams.rightMargin = DisplayUtil.dip2px(24, displayParams.scale);


        reset.setLayoutParams(layoutParams);

        reset.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetButtonClicked();
                }
            }
        );
        constraintLayout.addView(reset);

        pause = new Button(this);
        pause.setId(R.id.button_pause);
        pause.setText(R.string.button_pause_name);

        layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

        //layoutParams.setMargins(dpToPix_8,dpToPix_8,dpToPix_8,dpToPix_8);
        layoutParams.rightMargin = dpToPix_8;

        pause.setLayoutParams(layoutParams);

        pause.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pauseButtonClicked();
                    }
                }
        );
        constraintLayout.addView(pause);

        guideline1 = new Guideline(this);
        guideline1.setId(R.id.guideline1);
        layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.orientation = ConstraintLayout.LayoutParams.HORIZONTAL;
        guideline1.setLayoutParams(layoutParams);
        constraintLayout.addView(guideline1);

        guideline2 = new Guideline(this);
        guideline2.setId(R.id.guideline2);
        layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.orientation = ConstraintLayout.LayoutParams.VERTICAL;
        guideline2.setLayoutParams(layoutParams);
        constraintLayout.addView(guideline2);


        save = new Button(this);
        save.setId(R.id.button_save);
        save.setText(R.string.button_save_name);

        layoutParams = new ConstraintLayout.LayoutParams(DisplayUtil.dip2px(113, displayParams.scale), DisplayUtil.dip2px(49, displayParams.scale));

        //layoutParams.setMargins(dpToPix_8,dpToPix_8,dpToPix_8,dpToPix_8);
        layoutParams.rightMargin = DisplayUtil.dip2px(24, displayParams.scale);

        save.setLayoutParams(layoutParams);

        save.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveButtonClicked();
                    }
                }
        );
        constraintLayout.addView(save);

        loadByFile = new Button(this);
        loadByFile.setId(R.id.button_load_from_file);
        loadByFile.setText(R.string.button_new_name);

        layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

        //layoutParams.setMargins(dpToPix_8,dpToPix_8,dpToPix_8,dpToPix_8);
        layoutParams.bottomMargin = dpToPix_8;
        layoutParams.leftMargin = dpToPix_8;

        loadByFile.setLayoutParams(layoutParams);

        loadByFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadByFileButtonClicked();
            }}
        );
        constraintLayout.addView(loadByFile);


        more = new  Button(this);
        more.setId(R.id.button_more);
        more.setText(R.string.button_more_name);
        layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

        //layoutParams.setMargins(dpToPix_8,dpToPix_8,dpToPix_8,dpToPix_8);
        layoutParams.rightMargin = dpToPix_8;

        more.setLayoutParams(layoutParams);

        constraintLayout.addView(more);

        if(mainViewModel == null){
            mainViewModel = new MainViewModel(this,level_string);
        }

        constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(f.getId(), ConstraintSet.BOTTOM, reset.getId(), ConstraintSet.TOP, 0);
        constraintSet.connect(f.getId(), ConstraintSet.END, constraintLayout.getId(), ConstraintSet.END, 0);
        constraintSet.connect(f.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START, 0);

        constraintSet.connect(level_spinner.getId(), ConstraintSet.BOTTOM, loadByFile.getId(), ConstraintSet.TOP, 0);
        constraintSet.connect(level_spinner.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START, dpToPix_8);
        constraintSet.connect(level_spinner.getId(), ConstraintSet.TOP, f.getId(), ConstraintSet.BOTTOM, 0);

        constraintSet.connect(reset.getId(), ConstraintSet.BOTTOM, save.getId(), ConstraintSet.TOP, 0);
        constraintSet.connect(reset.getId(), ConstraintSet.END, pause.getId(), ConstraintSet.START,  DisplayUtil.dip2px(55, displayParams.scale));


        constraintSet.connect(pause.getId(), ConstraintSet.BASELINE, reset.getId(), ConstraintSet.BASELINE, 0);
        constraintSet.connect(pause.getId(), ConstraintSet.END, constraintLayout.getId(), ConstraintSet.END, dpToPix_8);

        constraintSet.setGuidelineBegin(guideline1.getId(), DisplayUtil.dip2px(451, displayParams.scale));

        constraintSet.connect(save.getId(), ConstraintSet.BASELINE, loadByFile.getId(), ConstraintSet.BASELINE, 0);
        constraintSet.connect(save.getId(), ConstraintSet.START, loadByFile.getId(), ConstraintSet.END, 0);
        constraintSet.connect(save.getId(), ConstraintSet.END, more.getId(), ConstraintSet.START, 0);

        constraintSet.connect(more.getId(), ConstraintSet.BASELINE, save.getId(), ConstraintSet.BASELINE, 0);
        constraintSet.connect(more.getId(), ConstraintSet.END, constraintLayout.getId(), ConstraintSet.END, dpToPix_8);

        constraintSet.connect(loadByFile.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM, 0);
        constraintSet.connect(loadByFile.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START, dpToPix_8);


        constraintSet.connect(textViewName.getId(), ConstraintSet.BASELINE, textViewMoveCount.getId(), ConstraintSet.BASELINE, 0);
        constraintSet.connect(textViewName.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START, dpToPix_8);

        constraintSet.connect(textViewMoveCount.getId(), ConstraintSet.BOTTOM, f.getId(), ConstraintSet.TOP, 0);
        constraintSet.connect(textViewMoveCount.getId(), ConstraintSet.START, textViewName.getId(), ConstraintSet.END, 0);
        constraintSet.connect(textViewMoveCount.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 0);

        constraintSet.setGuidelineBegin(guideline2.getId(), DisplayUtil.dip2px(264, displayParams.scale));

        constraintSet.connect(help.getId(), ConstraintSet.BASELINE, textViewMoveCount.getId(), ConstraintSet.BASELINE, 0);
        constraintSet.connect(help.getId(), ConstraintSet.END, constraintLayout.getId(), ConstraintSet.END, 0);
        constraintSet.setHorizontalBias(help.getId(),0.0F);
        constraintSet.connect(help.getId(), ConstraintSet.START, textViewMoveCount.getId(), ConstraintSet.END, DisplayUtil.dip2px(185, displayParams.scale));


        constraintSet.applyTo(constraintLayout);
    }

    private boolean roleViewOnTouched(MotionEvent event){
        rolePointXShort = theView.getRolePointXShort();
        rolePointXLong = theView.getRolePointXLong();
        rolePointYShort = theView.getRolePointYShort();
        rolePointYLong = theView.getRolePointYLong();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX=event.getX();
                startY=event.getY();

                if(mainViewModel.moveThe(rolePointXShort,rolePointXLong,rolePointYShort,rolePointYLong,startX,startY)){

                    setParas();

                    theView.invalidate();

                    if(mainViewModel.getGameModel().getTheseus().isHasWon()){
                        playWin();
                        theWinDialog();
                    }
                }

                if(mainViewModel.moveMin()){
                    if(mainViewModel.getGameModel().getMinotaur().isHasEaten()){
                        minView.bringToFront();
                        playLost();
                        minKillTheDialog();
                    }
                    setParas();
                    minView.invalidate();

                    //MapView m = (MapView)constraintLayout.getViewById(R.id.minview);
                }

                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if(mainViewModel.moveMin()){

                    if(mainViewModel.getGameModel().getMinotaur().isHasEaten()){

                        minView.bringToFront();
                        playLost();
                        minKillTheDialog();
                    }
                    setParas();
                    minView.invalidate();
                }
                break;
            default:
                return false;
        }


        Log.d(TAG, "Touch Event::" + event.getAction());
        return true;
    }

    private void setParas(){
        ViewBindingAdapter.setThePointStr(mapView, mainViewModel.thePointStr.get());
        ViewBindingAdapter.setHeightStr(theView, mainViewModel.heightStr.get());
        ViewBindingAdapter.setItemsWallAboveStr(mapView, mainViewModel.wallAbovePointListStr.get());
        ViewBindingAdapter.setItemsWallLeftStr(mapView, mainViewModel.wallLeftPointListStr.get());
        ViewBindingAdapter.setMinPointStr(mapView, mainViewModel.minPointStr.get());
        ViewBindingAdapter.setPointStr(theView, mainViewModel.thePointStr.get());
        ViewBindingAdapter.setPointStr(minView,mainViewModel.minPointStr.get());
        ViewBindingAdapter.setWallSquareStr(mapView, mainViewModel.wallSquareStr.get());
        minView.setWallSquareStr(mainViewModel.wallSquareStr.get());
        theView.setWallSquareStr(mainViewModel.wallSquareStr.get());
        textViewMoveCount.setText(mainViewModel.moveCount.get());
    }


    /**
     * spinnerItemSelected
     */
    private void spinnerItemSelected(){
        String aNewlevel_string = (String) level_spinner.getSelectedItem();

        if(!level_string.equals(aNewlevel_string)){
            level_string = aNewlevel_string;
            if(mainViewModel == null){
                mainViewModel = new MainViewModel(context,aNewlevel_string);
            }else{
                mainViewModel.initGameImpl(aNewlevel_string);
                theView.bringToFront();
                setParas();
                mapView.invalidate();
                theView.invalidate();
                minView.invalidate();
            }
        }
    }

    private void resetButtonClicked(){
        mainViewModel.initGameImpl(level_string);
        setParas();
        theView.bringToFront();
        mapView.invalidate();
        theView.invalidate();
        minView.invalidate();
    }

    private void pauseButtonClicked(){

        mainViewModel.moveMin();
        mainViewModel.moveMin();

        if(mainViewModel.getGameModel().getMinotaur().isHasEaten()){
            setParas();
            minView.bringToFront();
            playLost();
            minKillTheDialog();
        }

        minView.invalidate();
    }

    private void saveButtonClicked(){
        mainViewModel.initGameImpl(level_string);

        directory = context.getFilesDir();

        fileP = directory.getAbsolutePath() + File.separator + Const.LEVEL_FILE_NAME.getValue();

        new Thread(new Runnable() {
            @Override
            public void run() {
                isLoadSuccessful =  mainViewModel.save(directory);
                Log.d(TAG,"Save to " + fileP + " successfully!" );
            }
        }).start();

        showSaveFileProgressDialog();
    }

    private void showSaveFileProgressDialog() {
        final int MAX_PROGRESS = 100;
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setProgress(0);
        progressDialog.setTitle("Saving");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(MAX_PROGRESS);
        progressDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                int progress= 0;

                while (progress < MAX_PROGRESS){
                    try {
                        Thread.sleep(100);
                        if(!isSaveSuccessful){
                            progress++;
                            progressDialog.setProgress(progress);
                        }else{
                            progressDialog.setProgress(MAX_PROGRESS);
                            progressDialog.cancel();
                            if(isSaveSuccessful){
                                isSaveSuccessful = false;
                            }
                        }

                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                progressDialog.cancel();
            }
        }).start();

    }

    private void loadByFileButtonClicked(){
        directory = context.getFilesDir();

        fileP = directory.getAbsolutePath() + File.separator + Const.LEVEL_FILE_NAME.getValue();

        new Thread(new Runnable() {
            @Override
            public void run() {
                isLoadSuccessful = mainViewModel.initGameImplByFile(level_string);
                Log.d(TAG,  "Load " + level_string + " from " + fileP + " successfully!" );
            }
        }).start();

        showLaodFileProgressDialog();


        fileP = directory.getAbsolutePath() + File.separator + Const.LEVEL_FILE_NAME.getValue();

    }

    private void  helpButtonClicked(){
        helpButtonDialog();
    }

    private void  helpButtonDialog(){

        final AlertDialog.Builder minKillTheDialog = new AlertDialog.Builder(this);

        minKillTheDialog.setTitle("HELP");

        minKillTheDialog.setMessage("As Theseus, you must escape the Minotaur's maze!\n" +
                "\n" +
                "For every move you make, the Minotaur makes two moves. Luckily, he isn't terribly bright. He will move toward Theseus, favoring horizontal over vertical moves, without knowing how to get around a wall in his way. Escape by luring the Minotaur into a place where he gets stuck!\n" +
                "\n" +
                "Code: Yang CHEN 99168512");

        minKillTheDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        minKillTheDialog.show();
    }

    private void showLaodFileProgressDialog() {
        final int MAX_PROGRESS = 100;
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setProgress(0);
        progressDialog.setTitle("Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(MAX_PROGRESS);
        progressDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                int progress= 0;

                while (progress < MAX_PROGRESS){
                    try {
                        Thread.sleep(100);
                        if(!isLoadSuccessful){
                            progress++;
                            progressDialog.setProgress(progress);
                        }else{
                            progressDialog.setProgress(MAX_PROGRESS);
                            progressDialog.cancel();
                            if(isLoadSuccessful){
                                isLoadSuccessful = false;
                            }
                            minView.invalidate();
                            theView.invalidate();
                        }

                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                progressDialog.cancel();
            }
        }).start();

    }



    private void minKillTheDialog(){

        final AlertDialog.Builder minKillTheDialog = new AlertDialog.Builder(this);

        minKillTheDialog.setTitle("Minotaur killed Theseus!");

        minKillTheDialog.setMessage("Game Over");

        minKillTheDialog.setPositiveButton("OK",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    minKillTheOptionDialog();
                }
         });
        minKillTheDialog.show();
    }

    private void minKillTheOptionDialog(){
        final AlertDialog.Builder theDialog = new AlertDialog.Builder(this);

        theDialog.setTitle("Do you like to play these level again?");

        theDialog.setMessage("");

        theDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                resetButtonClicked();
            }
        });

        theDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                level_string = "Level-1";
                resetButtonClicked();
                level_spinner.setSelection(0);
            }
        });
        theDialog.show();
    }

    private void theWinDialog(){

        final AlertDialog.Builder minKillTheDialog = new AlertDialog.Builder(this);

        minKillTheDialog.setTitle("Theseus win!");

        minKillTheDialog.setMessage("Congratulations~");

        minKillTheDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        theWinOptionDialog();
                    }
                });
        minKillTheDialog.show();
    }

    private void theWinOptionDialog(){
        final AlertDialog.Builder theDialog = new AlertDialog.Builder(this);

        theDialog.setTitle("Do you like to play these level again?");

        theDialog.setMessage("");

        theDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                resetButtonClicked();
            }
        });

        theDialog.setNegativeButton("Next Level", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                int theNum = mainViewModel.getGameModel().getLevelByLevelStr(level_string);
                level_string = mainViewModel.getGameModel().getLevels()[theNum];
                resetButtonClicked();
                level_spinner.setSelection(theNum);
            }
        });
        theDialog.show();
    }


    public void playWin() {

        MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.you_win_sound_effect);
        mediaPlayer.start();
    }

    public void playLost() {

        MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.game_over_sound_effect);
        mediaPlayer.start();
    }

}
