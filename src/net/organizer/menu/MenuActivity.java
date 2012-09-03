package net.organizer.menu;

import net.organizer.config.ConfigActivity;
import net.organizer.web.WebActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import net.organizer.R;

public class MenuActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);

		// //tymczasowo do testow////
		// Data.User.login = "damiancyk";
		// Data.User.password = "haslo";
		// //////////////////////////

		TextView textViewMenuHello = (TextView) findViewById(R.id.textViewMenuHello);
		textViewMenuHello
				.setText("Witaj! \nOto sieciowy, wielouzytkownikowy organizer czasu!");

		Button buttonMenuNote = (Button) findViewById(R.id.buttonMenuNote);
		buttonMenuNote.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(MenuActivity.this,
						ConfigActivity.class));
			}
		});

		Button buttonWeb = (Button) findViewById(R.id.buttonMenuWeb);
		buttonWeb.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(MenuActivity.this, WebActivity.class));
			}
		});

	}

}
