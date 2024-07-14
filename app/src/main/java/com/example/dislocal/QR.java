package com.example.dislocal;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.List;


public class QR extends AppCompatActivity {

    private DecoratedBarcodeView barcodeView;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_qr);

        sessionManager = new SessionManager(getApplicationContext());

        if (!sessionManager.isLoggedIn()) {
            Intent intent = new Intent(QR.this, Login.class);
            startActivity(intent);
            finish();
            return;
        }

        //Check permission
        if (ActivityCompat.checkSelfPermission(QR.this,
                android.Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED) {
            //get current location when permission is granted
            barcodeView = findViewById(R.id.barcode_scanner);
            barcodeView.decodeContinuous(new BarcodeCallback() {
                @Override
                public void barcodeResult(BarcodeResult result) {
                    if (result.getText() != null) {
                        // Show the dialog with QR code details
                        showDialog(result.getText());
                    }
                }

                @Override
                public void possibleResultPoints(List<ResultPoint> resultPoints) {
                    // Handle possible result points
                }
            });

        } else {
            //request permission when permission is denied
            ActivityCompat.requestPermissions(QR.this,
                    new String[]{android.Manifest.permission.CAMERA}, 44);
        }

        ImageView homenav = findViewById(R.id.homenav);
        homenav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QR.this, Home.class);
                startActivity(intent);
            }
        });

        ImageView aboutnav = findViewById(R.id.aboutnav);
        aboutnav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QR.this, About.class);
                startActivity(intent);
            }
        });

        ImageView discovernav = findViewById(R.id.discovernav);
        discovernav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QR.this, Discover.class);
                startActivity(intent);
            }
        });

        ImageView feedbacknav = findViewById(R.id.feedbacknav);
        feedbacknav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QR.this, Feedback.class);
                startActivity(intent);
            }
        });

        ImageView profilenav = findViewById(R.id.profilenav);
        profilenav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QR.this, Profile.class);
                startActivity(intent);
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void showDialog(String qrCodeText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(dialogueTitle(qrCodeText));
        builder.setMessage(dialogueDsc(qrCodeText));
        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss();
            // If you want to pass the result back to the previous activity
            Intent intent = new Intent();
            intent.putExtra("qrCode", qrCodeText);
            setResult(RESULT_OK, intent);
            finish();
        });
        builder.show();
    }

    private String dialogueTitle(String qrCodeText){
        String text = "";

        if (qrCodeText.equals("arts")){
            text = "Selangor Islamic Arts Garden Complex";
        }
        else if (qrCodeText.equals("masjid")){
            text = "Sultan Salahuddin Abdul Aziz Shah Mosque";
        }
        else if (qrCodeText.equals("muzium")){
            text = "Muzium Sultan Alam Shah";
        }
        else if (qrCodeText.equals("botani")){
            text = "Taman Botani Negara Shah Alam / National Botanic Garden";
        }
        else if (qrCodeText.equals("lake")){
            text = "Shah Alam Lake Garden";
        }
        else if (qrCodeText.equals("extreme")){
            text = "Shah Alam Extreme Park";
        }
        else if (qrCodeText.equals("icity")){
            text = "i-City Theme Park: A Night of Dazzling Lights";
        }

        return text;
    }

    private String dialogueDsc(String qrCodeText){
        String text = "";

        if (qrCodeText.equals("arts")){
            text = "Category: Cultural centre\n" +
                    "\n" +
                    "Location: \n" +
                    "Kompleks Taman Seni Islam Selangor, \n" +
                    "2A Persiaran Damai, \n" +
                    "Seksyen 10, 40100 Shah Alam, Selangor\n" +
                    "\n" +
                    "Opening hours:\n" +
                    "Tuesday - Sunday: 9.30am - 5.30pm\n" +
                    "\n" +
                    "Admission fee: Free\n" +
                    "\n" +
                    "Time required: 1.5 -2 hours\n" +
                    "\n" +
                    "About:\n" +
                    "- Built on a 14-hectare site in the city of Shah Alam, this spiritual sanctuary is the heart of Islamic arts heritage in Selangor.\n" +
                    "- It houses three permanent galleries and a private educational institute for higher learning of fine arts. The galleries mainly focus on representing and nourishing Islamic art in the country with special exhibits of Prophet Muhammad, Islamic and Urdu calligraphy found on mosques and palaces, holy Quran manuscripts, etc.\n" +
                    "- Among the most notable displays here is an original Quran manuscript said to be worth RM20 million.\n" +
                    "- Visitors get the opportunity to watch skilled artisans at work, as well as how the Quran is preserved, copied and produced. Occasionally, traditional Islamic performances are also held at the garden complex.\n" +
                    "\n" +
                    "Background/History:\n" +
                    "It serves as the exhibition and production centre for Yayasan Restu (Restu Foundation), a non-profit organisation formed in 1987 that aims to revive, preserve and promote Islamic arts and culture. Yayasan Restu was the first in Malaysia to produce fully handwritten and illuminated mushaf (hard copies of the Quran), and has held exhibitions in several countries worldwide.";
        }
        else if (qrCodeText.equals("masjid")){
            text = "Category: Cultural centre\n" +
                    "\n" +
                    "Location: \n" +
                    "Persiaran Masjid St., \n" +
                    "Sekysen 14, 40000 Shah Alam, Selangor.\n" +
                    "\n" +
                    "Opening hours:\n" +
                    "Monday to Thursday: 9:00am - 12:00pm;  2:00pm - 4:00pm\n" +
                    "Friday: Open only for Muslims\n" +
                    "Saturday, Sunday and Public Holidays: 9:00am - 12:00pm; 2:00pm - 4:00pm; 5:00pm - 6:30pm\n" +
                    "\n" +
                    "Admission fee: Free\n" +
                    "\n" +
                    "Time required: Less than 1 hour\n" +
                    "\n" +
                    "About:\n" +
                    "- Overlooking the Garden of Islamic Arts is the magnificent Sultan Salahuddin Abdul Aziz Shah Mosque, the state mosque of Selangor. Amazingly, it is the second largest mosque in Southeast Asia and the largest in Malaysia!\n" +
                    "- A well-known landmark, the gleaming dome of Sultan Salahuddin Abdul Aziz Mosque, also known as Shah Alam’s Blue Mosque, is often the first thing visitors entering the city notice. Capable of accommodating up to 24,000 worshipers at any one time\n" +
                    "\n" +
                    "\n" +
                    "Background/History:\n" +
                    "Named after Selangor's late Sultan who commissioned it in 1982, the mosque is also known as the 'Blue Mosque'. Four 142-meters high minarets standing tall at each of its corners are the world's tallest group of minarets. Also, it can accommodate around 24,000 devotees at a time. The blue stained glass of the mosque fills the ornate building with glimmering blue light. The 9 galleries here display an artistic blend of Islamic arts such as fine decorative calligraphy, painting and sculptures.\n";
        }
        else if (qrCodeText.equals("muzium")){
            text = "Category: Cultural centre\n" +
                    "\n" +
                    "Location: \n" +
                    "Muzium Sultan Alam Shah,\n" +
                    "Persiaran Bandaraya, Seksyen 14,\n" +
                    "40000 Shah Alam,\n" +
                    "Selangor.\n" +
                    "\n" +
                    "Opening hours:\n" +
                    "Tuesday – Sunday: 9.30am – 5.30pm\n" +
                    "\n" +
                    "Admission fee: Free\n" +
                    "\n" +
                    "Time required: More than 2 hours\n" +
                    "\n" +
                    "About:\n" +
                    "The museum’s building design is based on Minangkabau architecture; the Minangkabau is the world’s largest matrilineal society, where property, family name and land pass down from mother to daughter.\n" +
                    "- Historical\n" +
                    "Contains prehistoric artefacts found near early human settlements in the state. Among the most notable exhibits include fragments of Đông Sơn drums from the Bronze Age, dating back to 600 BC. This hall also tells the history of Selangor from the times of the Portuguese, Dutch and Bugis empires, Melaka and Johor Sultanates to Independence Day and beyond.\n" +
                    "- Arts & Culture\n" +
                    "The unique culture and lifestyle of various ethnic groups in Selangor including Bugis, Javanese, Minang, Banjar, Mandailing, Rawa and others are highlighted through traditional, literary and artistic displays including dioramas, clothing, musical instruments, tools and weapons.\n" +
                    "- Environmental\n" +
                    "Selangor’s rich biodiversity is illustrated through preserved animal specimens and life-like dioramas. Among the most interesting displays include a replica of “Crocodile Puchong”, the largest crocodile found in Peninsular Malaysia. In addition, visitors can experience entering a replica stone cave.\n" +
                    "\n" +
                    "- Sports\n" +
                    "Highlights the history of sports and famous sportsmen in the state. The best moments in various sports have been immortalised here for the appreciation of present and future generations.\n" +
                    "\n" +
                    "Islamic\n" +
                    "\n" +
                    "Focuses on the history, arrival and growth of Islam in Selangor. Some of the artifacts on display include models of old mosques, pulpits, drums, domes, ablution jars, pottery, utensils, weapons and old books. Also on display is the pulpit of Selayang Mosque from 1880, believed to be the oldest in the state.\n" +
                    "\n" +
                    "Outdoor\n" +
                    "\n" +
                    "Outdoor displays include a Ferret armored car, radar systems, airplanes, locomotives and the former Chief Minister of Selangor’s first car.\n" +
                    "\n" +
                    "\n" +
                    "Background/History:\n" +
                    "Covering approximately 135,000 square feet, the museum has nearly 4,000 artifacts housed in five exhibition halls and a section for outdoor displays, including:\n";
        }
        else if (qrCodeText.equals("botani")){
            text = "Category: Scenic park\n" +
                    "\n" +
                    "Location: \n" +
                    "Bukit Cahaya Seri Alam, \n" +
                    "Taman Botani Negara,\n" +
                    "40000 Shah Alam, Selangor\n" +
                    "\n" +
                    "Opening hours:\n" +
                    "Tuesday to Sunday- 7:30am to 4:30pm\n" +
                    "Closed on Monday (except school & public holidays)\n" +
                    "\n" +
                    "Admission fee: RM3\n" +
                    "\n" +
                    "Time required: More than 2 hours\n" +
                    "\n" +
                    "About:\n" +
                    "- Home to plenty of vegetation gardens, lakes, petting zoo, a seasonal temperature house, public pool, etc. They host activities from lightweight to extreme adventurous sports like hiking, horse riding, rock climbing or fox flying among other things. You are free to walk or rent a bike to explore the gardens and stop at the watchtowers to bask in the scenic views or relax and take a break at the gazebo. A perfectly splendid way to spend time with nature.\n" +
                    "\n" +
                    "Background/History:\n" +
                    "Previously known as TPM and which was initially known as Bukit Cerakah Agriculture Park was developed on 24th April 1986. The objective for the development of TBNSA is to create a national centre of excellence within the fields of education, scientific research and conservation for the development of botanical, horticulture, agriculture and other various related fields.";
        }
        else if (qrCodeText.equals("lake")){
            text = "Category: Scenic park\n" +
                    "\n" +
                    "Location: \n" +
                    "Pesiaran Tasik, Seksyen 14,\n" +
                    "40000 Shah Alam,\n" +
                    "Selangor, Malaysia.\n" +
                    "\n" +
                    "Opening hours:\n" +
                    "Always open\n" +
                    "\n" +
                    "Admission fee: Free\n" +
                    "\n" +
                    "Time required: 2 - 3 hours\n" +
                    "\n" +
                    "About:\n" +
                    "- This massive garden is a favourite retreat of the nearby residents, with its beautiful landscape and scenic pathways. Some sections of the lake are elevated so visitors can walk over and watch the aquatic life, like fish and tortoises. One may even catch a glimpse of an occasional peacock, goose, or stork! Some other popular things to do at the park include taking a splash at the waterpark - Wet World, indulging in Malaysian cuisine at the floating seafood restaurant, or going kayaking.\n" +
                    "\n" +
                    "- There are 3 man-made lakes (Central, Western & Eastern Lakes), each with its own unique features and characteristics.\n" +
                    "\n" +
                    "- Visitors may rent paddle boats and kayaks at ±RM10/hour to explore the interconnected lakes. Fishing competitions are regularly held, as are Majlis Pelepasan Ikan (fish releases).\n" +
                    "\n" +
                    "- Wet World Shah Alam:\n" +
                    "\n" +
                    "The Western Lake at Shah Alam Lake Gardens is home to Wet World Shah Alam, an award-winning, Caribbean-inspired water theme park.\n" +
                    "\n" +
                    "- Blue Mosque, International Islamic Arts Garden Complex & Sultan Alam Shah Museum\n" +
                    "The Eastern Lake is located within walking distance (±500m) from the above mentioned places.\n" +
                    "\n" +
                    "Background/History:\n" +
                    "Sprawled over 43 hectares, the Shah Alam Lake Gardens, designed by renowned landscape architect Fumiaki Takano, was the first public park to open in Selangor in 1985. The lake gardens are managed by Shah Alam’s city council (MBSA), who have done well to ensure that the park maintains its popularity among locals and tourists alike.\n" +
                    "Though smaller than its neighbour, Taman Botani Negara (National Botanical Gardens), the Shah Alam Lake Gardens have plenty to offer.\n";
        }
        else if (qrCodeText.equals("extreme")){
            text = "Category: Hidden gem\n" +
                    "\n" +
                    "Location:\n" +
                    "Jalan Lompat Pagar 13/37, \n" +
                    "Seksyen 13, \n" +
                    "40100 Shah Alam, \n" +
                    "Selangor, Malaysia.\n" +
                    "\n" +
                    "Opening hours: 8:30am - 5:30pm\n" +
                    "\n" +
                    "Admission fee: Free\n" +
                    "\n" +
                    "Time required: 1 - 3 hours\n" +
                    "\n" +
                    "About:\n" +
                    "For those seeking a rush of adrenaline, Shah Alam Extreme Park is the place to be. This multi-sport facility offers a range of activities, from skateboarding, wall climbing, to paintball, catering to adventure enthusiasts of all ages. The park’s circuits vary in difficulty, making it an exciting destination for both beginners and experienced adventurers. It’s not just about the thrill; it’s about challenging yourself and creating memories in the great outdoors of Shah Alam.";
        }
        else if (qrCodeText.equals("icity")){
            text = "Category: Hidden gem\n" +
                    "\n" +
                    "Location:\n" +
                    "i-Gallery, \n" +
                    "Jalan Multimedia, I-City, \n" +
                    "40000 Shah Alam, \n" +
                    "Selangor, Malaysia.\n" +
                    "\n" +
                    "Opening hours:\n" +
                    "Everyday: 5pm - 12am\n" +
                    "\n" +
                    "Admission fee: Free\n" +
                    "\n" +
                    "Time required: 1 - 3 hours\n" +
                    "\n" +
                    "About:\n" +
                    "Recognised by CNN as one the Top 25 Brightest Places in the World. A must visit for those travelling to Shah Alam. Digitally integrated development with shops, hotels, a mall, ice sculptures & an amusement park.";
        }

        return text;
    }

    @Override
    protected void onResume() {
        super.onResume();
        barcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        barcodeView.pause();
    }


}