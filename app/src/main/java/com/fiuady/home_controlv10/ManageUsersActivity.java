package com.fiuady.home_controlv10;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fiuady.home_controlv10.db.Account;
import com.fiuady.home_controlv10.db.AccountHelper;
import com.fiuady.home_controlv10.db.Cuentas;

import java.util.List;

public class ManageUsersActivity extends AppCompatActivity implements OnDialogClickListener {
    private static final String TAG ="AddAccountOption";
    private static final String TAG2 ="ModifyAccountOption";
    private class AccountsHolder extends RecyclerView.ViewHolder{
        private TextView txtUserId;
        private TextView txtUserName;
        private TextView txtUserEmail;
        private TextView txtUserPassword;
        private ImageView imageOptions;
        public AccountsHolder(View itemView){
            super(itemView);
            txtUserId = (TextView)itemView.findViewById(R.id.UserId_Description);
            txtUserName = (TextView)itemView.findViewById(R.id.UserName_Description);
            txtUserEmail = (TextView)itemView.findViewById(R.id.UserEmail_Description);
            txtUserPassword = (TextView)itemView.findViewById(R.id.UserPassword_Description);
            imageOptions = (ImageView)itemView.findViewById(R.id.UserSetting_View);
        }
        public void bindAccount(Cuentas cuenta)
        {
            txtUserId.setText(String.valueOf(cuenta.getId()));
            txtUserName.setText(cuenta.getUser());
            txtUserEmail.setText(cuenta.getMail());
            txtUserPassword.setText(cuenta.getPassword());
        }
    }
    private class AccountAdapter extends RecyclerView.Adapter<AccountsHolder>{
        private List<Cuentas> accounts;
        public AccountAdapter(List<Cuentas>accounts){
            this.accounts = accounts;
        }

        @Override
        public AccountsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= getLayoutInflater().inflate(R.layout.activity_manage_users_item,parent,false);
            return new AccountsHolder(view);
        }

        @Override
        public void onBindViewHolder(AccountsHolder holder, int position) {
            holder.bindAccount(accounts.get(position));
        }

        @Override
        public int getItemCount() {
            return accounts.size();
        }

        public List<Cuentas> getAccounts() {
            return accounts;
        }
    }

    Account account;
    private RecyclerView recyclerView;
    private AccountAdapter adapter;
    ImageView imageView;
    PopupMenu popupMenu;
    private String  AccountName;
    private int AccountId;
    private String  AccountEmail;
    private String AccountPassword;
    private String AccountPin;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);
        Toolbar toolbar = (Toolbar)findViewById(R.id.my_users_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Accounts Manager");
        account= new Account(getApplicationContext());
        recyclerView = (RecyclerView)findViewById(R.id.users_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Cuentas> accounts =account.Getallaccounts();
        adapter = new AccountAdapter(accounts);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemOnClickListener(getApplicationContext(), recyclerView, new RecyclerItemOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                imageView = (ImageView)view.findViewById(R.id.UserSetting_View);

                // value_ProductId =Integer.valueOf(txt_ProductId.getText().toString());
                Cuentas account  = adapter.getAccounts().get(position);
                  AccountName = account.getUser();
                 AccountId = account.getId();
                AccountEmail = account.getMail();
                AccountPassword = account.getPassword();
                AccountPin = account.getPin();





                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        popupMenu = new PopupMenu(ManageUsersActivity.this, v);
                        popupMenu.getMenuInflater().inflate(R.menu.account_options_menu,popupMenu.getMenu());
                        PopupItemClickListener(popupMenu);
                        popupMenu.show();
                    }
                });

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));






    }
    private void PopupItemClickListener(PopupMenu popupMenu){
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.option_ModifyAccount:
                       /* Intent intent = new Intent(Assemblies_Activity.this, EditAssembly_Activity.class);
                        intent.putExtra(EditAssembly_Activity.EXTRA_ASSEMBLY_DESCRIPTION,Assembly_Description);
                        startActivityForResult(intent,CODE_EDIT);*/
                        FragmentManager fragmentManager = getFragmentManager();
                        Fragment frag = fragmentManager.findFragmentByTag(TAG2);
                        if (frag != null) {
                            fragmentManager.beginTransaction().remove(frag).commit();
                        }
                        MyGeneralDialog myGeneralDialog= MyGeneralDialog.newInstance(AccountId,AccountName,AccountEmail,AccountPassword,AccountPin,0);
                        myGeneralDialog.show(fragmentManager, TAG2);
                        break;
                    case R.id.option_DeleteAccount:
                        account.Delete_account(String.valueOf(AccountId));
                        UpdateRecycler();
                        break;

                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_user_menu,menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_user:
                //Intent intent = new Intent(Assemblies_Activity.this, AddAssembly_Activity.class );
                //startActivityForResult(intent,EXTRA_CODE);
                //To add User
                FragmentManager fragmentManager = getFragmentManager();
                Fragment frag = fragmentManager.findFragmentByTag(TAG);
                if (frag != null) {
                    fragmentManager.beginTransaction().remove(frag).commit();
                }
                MyGeneralDialog myGeneralDialog= MyGeneralDialog.newInstance(0,"","","","",1);
                myGeneralDialog.show(fragmentManager, TAG);
                break;

        }
        return super.onOptionsItemSelected(item);


    }
    public void onDialogPositiveClick(DialogFragment dialogFragment, int code, String Name, String Email, String Password, String Pin, int accountId)
    {
        if(code ==1)//add
        {
            account.CreateNonAdminAccount(Name,Email,Password,Pin);
            UpdateRecycler();


        }
        else
        {
            account.modify_account(String.valueOf(accountId),Name,Password,Pin);
            UpdateRecycler();

        }

    }
    public void onDialogNegativeClick(DialogFragment dialogFragment, int code, String Name, String Email, String Password, String Pin, int accountId)
    {

    }

    public void UpdateRecycler()
    {
        List<Cuentas> accounts =account.Getallaccounts();
        adapter = new AccountAdapter(accounts);
        recyclerView.setAdapter(adapter);

    }
}
