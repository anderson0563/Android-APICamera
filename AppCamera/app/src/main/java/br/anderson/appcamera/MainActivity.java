package br.anderson.appcamera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Bitmap b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.foto);
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            b=imageBitmap;
            imageView.setImageBitmap(imageBitmap);
        }
    }

    public void limparFoto(View v){
        imageView.setImageDrawable(null);
        ((EditText)findViewById(R.id.nomeFoto)).setText("");
    }


    public void foto(View v) {
        dispatchTakePictureIntent();
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String nomeArquivo = ((EditText)findViewById(R.id.nomeFoto)).getText().toString();
        File image = new File(storageDir, nomeArquivo + ".png");
        return image;
    }

    public void fotoSalvar(View v) throws IOException {
        FileOutputStream outStream = new FileOutputStream(createImageFile());
        b.compress(Bitmap.CompressFormat.PNG, 100, outStream);
        outStream.close();
        Toast.makeText(this, "Foto Salva com Sucesso", Toast.LENGTH_SHORT).show();
    }


}
