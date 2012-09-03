package net.organizer.config;

import net.organizer.menu.MenuActivity;
import net.organizer.rest.Data;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import net.organizer.R;

public class ConfigActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.config);

		Button buttonConfigReturn = (Button) findViewById(R.id.buttonConfigReturn);
		buttonConfigReturn.setText("powrot");
		buttonConfigReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(ConfigActivity.this,
						MenuActivity.class));
			}
		});

		final EditText editTextConfigLogin = (EditText) findViewById(R.id.editTextConfigLogin);
		editTextConfigLogin.setText(Data.User.login);
		final EditText editTextConfigPassword = (EditText) findViewById(R.id.editTextConfigPassword);
		editTextConfigPassword.setText(Data.User.password);

		Button buttonConfigSave = (Button) findViewById(R.id.buttonConfigSave);
		buttonConfigSave.setText("zapisz");
		buttonConfigSave.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				saveConfig(editTextConfigLogin, editTextConfigPassword);
				Toast.makeText(ConfigActivity.this, "Zapisano zmiany",
						Toast.LENGTH_LONG).show();
				startActivity(new Intent(ConfigActivity.this,
						MenuActivity.class));
			}
		});
 
	}

	private void saveConfig(EditText editTextConfigLogin,
			EditText editTextConfigPassword) {
		if (editTextConfigLogin != null && editTextConfigPassword != null) {
			Data.User.login = editTextConfigLogin.getText().toString();
			Data.User.password = editTextConfigPassword.getText().toString();
		}
	}

}
