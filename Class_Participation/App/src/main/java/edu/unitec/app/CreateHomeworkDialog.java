package edu.unitec.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

/**
 * Created by Alberto on 11/06/2014.
 */
public class CreateHomeworkDialog extends DialogFragment {
    Section currentSection;
    EditText homeworkName;

    CreateHomeworkDialog(Section currentSection)
    {
        this.currentSection = currentSection;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_create_homework, null);

        homeworkName = (EditText)view.findViewById(R.id.editTextCreateHomeworkName);

        homeworkName.requestFocus();

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Student");
        builder.setView(view);

        builder.setNegativeButton("Close", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                CreateHomeworkDialog.this.getDialog().cancel();
                getActivity().finish();
            }
        });

        builder.setPositiveButton("Create",
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        //Do nothing here because we override this button later to change the close behaviour.
                        //However, we still need this because on older versions of Android unless we
                        //pass a handler the button doesn't get instantiated
                    }
                });

        return builder.create();
    }

    @Override
    public void onStart(){
        super.onStart();    //super.onStart() is where dialog.show() is actually called on the underlying dialog, so we have to do it after this point
        AlertDialog dialog = (AlertDialog)getDialog();

        if(dialog != null){
            Button positiveButton = (Button) dialog.getButton(Dialog.BUTTON_POSITIVE);

            assert positiveButton != null;
            positiveButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Boolean wantToCloseDialog = false;

                    if(homeworkName.getText().toString().isEmpty()){
                        homeworkName.requestFocus();
                    }else{
                        Homework homework = new Homework(homeworkName.getText().toString(),
                                currentSection.get_SectionId());

                        try{
                            DatabaseHandler db = new DatabaseHandler(v.getContext());
                            if(!db.homeworkExist(homework,currentSection.get_SectionId())){
                                db.addHomework(homework);
                            }
                            ((CreateHomework)getActivity()).homework=homework;
                            getActivity().setTitle(homeworkName.getText().toString());
                            db.close();
                        }catch (Exception e){

                        }
                        wantToCloseDialog = true;
                    }

                    if(wantToCloseDialog){
                        dismiss();
                    }
                }
            });
        }

    }
}