package com.example.fahmiardiansyahbangkit2023_crushgearturbo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    companion object{val INTENT_PARCELABLE = "OBJECT_INTENT"}



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val crushgearturbo_List = listOf<CrushGearTurbo>(

            CrushGearTurbo(R.drawable.ark_cavalier, "Ark Cavalier", "アークキャバリアー " + " (Ākukyabariā)" ,
                "Ark Cavalier adalah Crush Gear yang diciptakan oleh Sean Firestone. Ini digunakan oleh Sukiyaking, alias Kouya Marino selama Gear Pancratum. Ark Cavalier hampir memiliki kemiripan yang sama dengan Garuda Eagle seperti yang terlihat di episode 50. Rincian:\n" +
                        "Penampilan pertama: Hakai Kikōshi Cain\n" +
                        "Nomor entri: GPSF-005-222AC\n" +
                        "Atribut: Bersinar\n" +
                        "Serangan spesial: Penghancur Cemerlang\n" +
                        "Digunakan oleh: Sukiyaking\n" +
                        "Dibuat oleh: Sean Firestone\n" +
                        "Informasi Teknis \n" +
                        "Panjang: 195,3 mm\n" +
                        "Lebar: 100,0 mm\n" +
                        "Tinggi: 45,3 mm\n" +
                        "Berat: 125 g\n" +
                        "Bahan penutup: CFRP\n" +
                        "Tipe motor: tCGM-003-D\n" +
                        "Revolusi motor: 14500 rpm\n" +
                        "Senjata utama: Argento Lancer\n" +
                        "VT Chassis: Ya (Tipe Spesial Dash)"
            ),
            CrushGearTurbo(R.drawable.blitz_vogel, "Blitz Vogel", "ブリッツフォーゲル " + " (Burittsufōgeru)"
                ,"Blitz Vogel adalah Crush Gear milik Harry Gamble, saingan Yuhya Marino. Pemanggilan Arwah Dewa Batu Api sangat tersirat pada bangunan alat ini.\n" +
                        "Penampilan pertama Moero! Giafaito Damashii\n" +
                        "Nomor entri GCGE-001-WRS-01BV\n" +
                        "Atribut Pelangi\n" +
                        "Digunakan oleh Harry Gamble\n" +
                        "Informasi Teknis\n" +
                        "Panjang 192,2 mm\n" +
                        "Lebar 90,0 mm\n" +
                        "Tinggi 62,8 mm\n" +
                        "Berat 140g\n" +
                        "Bahan penutup CFRP\n" +
                        "Tipe motor CGM-001-N\n" +
                        "Putaran motor 15330 rpm\n" +
                        "Senjata utama Eisen Schnabel\n" +
                        "Chasis VT: Tidak Ada" ),
            CrushGearTurbo(R.drawable.cuty_zebra, "Cuty Zebra", "キューティーゼブラ " + " (Kyūtīzebura)" ,
                "Cuty Zebra merupakan Crush Gear yang dimiliki oleh Koume Aida, salah satu anggota Pink Lips Team. Lapisan penutup Cuty Zebra terbuat dari lateks dan memiliki sasis khusus yang seimbang, yang dirancang oleh Koume sendiri. Kombinasi dari pengaturan ini membuat Gear lawan mendarat kembali di atas rodanya terlepas dari jenis serangan apa yang digunakannya. Oleh karena itu, Koume menggambarkan Cuty Zebra sebagai Crush Gear dengan sentuhan wanita. Gear juga terkenal karena kecepatannya.\n" +
                        "\n" +
                        "Cuty Zebra digunakan selama pertandingan Koume melawan Kuroudo di Illusion Cup. Gear Kuroudo, Shooting Mirage mengalami kesulitan dalam mengalahkan Gear selama pertarungan. Namun, Cuty Zebra dikalahkan setelah Kuroudo mengetahui salah satu penghalang cincin itu dan menggunakannya untuk keuntungannya." ),
            CrushGearTurbo(R.drawable.dino_phalanx, "Dino Phalanx", "ディノファランクス " + " (Dinofarankusu)",
                "Dino Phalanx adalah Crush Gear yang dimiliki oleh Kyousuke Jin, dan dibuat olehnya bersama dengan Kuroudo Marume. Dino Phalanx adalah versi upgrade dari Dino Spartan. Konsep desain Dino Phalanx terungkap selama pertandingan terakhir Jin melawan Kishin di lautan di mana Master Catur akhirnya dikalahkan oleh Master Gear. Sama seperti pendahulunya, ia juga dilengkapi dengan VT Chassis.\n" +
                        "\n" +
                        "Nomor masuk JCGA-005-TBC-03DP\n" +
                        "Atribut Api\n" +
                        "Serangan khusus Pemintal Api\n" +
                        "Digunakan dan Dibuat oleh Kyousuke Jin\n" +
                        "Panjang 167,0 mm\n" +
                        "Lebar 111,0 mm\n" +
                        "Tinggi 61,0 mm\n" +
                        "Berat 105g\n" +
                        "Bahan penutup CFRP\n" +
                        "Tipe motor CGM-001-N\n" +
                        "Putaran motor 15000 rpm\n" +
                        "Senjata utama Gergaji Uzz Ganda\n" +
                        "Chasiss VT Ya\n" ),
            CrushGearTurbo(R.drawable.gaiki, "Gaiki", "鎧輝 " + " (Gaiki)",
                "Gaiki adalah Crush Gear yang dimiliki oleh Takeshi Manganji, dan dibuat serta didesain oleh Grup Manganji. Sebelum pembuatan Garuda Phoenix dan meskipun VT Chassis bocor, Gaiki dianggap sebagai gear terkuat di dunia namun dikalahkan oleh Garuda Phoenix. Terlepas dari hal tersebut masih merupakan mesin yang sangat kuat karena mampu melaju ke final Piala Dunia dengan sedikit usaha. Gaiki adalah Gear pertama di Crush Gear Turbo yang dilengkapi dengan  kemampuan untuk menggeser kotak baterai Gear baik ke depan atau ke belakang. Gaiki dikatakan sebagai revolusi dalam teknologi Crush Gear, Takeshi menggunakan Gaiki melawan Garuda Eagle milik Kouya. Hal ini menyebabkan Garuda Eagle rusak parah akibat pertempuran dan Kouya mengalami kekalahan yang memalukan. Di episode 14 dari serial tersebut, dia menggunakan serangan spesial Gaiki, King of the Dragon Fighters untuk pertama kalinya melawan lawannya Daisuke Sasaki. Takeshi sering mengandalkan King of the Dragon Fighters hampir di setiap pertandingan karena mampu mengobrak-abrik gear lawan . Selain itu, serangan spesialnya, King of the Dragon Fighters, hampir tidak dapat dihindari - terutama saat seseorang mencoba melarikan diri"),
            CrushGearTurbo(R.drawable.garuda_eagle, "Garuda Eagle", "ガルダイーグル " + " (Garudaīguru)",
                "Garuda Eagle adalah perlengkapan naksir yang dimiliki oleh Kouya Marino. Sebelumnya itu milik saudaranya, Yuhya Marino yang ia gunakan untuk Piala Dunia. Garuda Eagle dirancang dan dibangun oleh Yuhya dan Alex Borg, seorang master gear Kanada yang dulunya adalah anggota Tobita Club. Sepanjang konstruksi Garuda Eagle, mampu menghasilkan sasis dan bagian -bagian lain dari resin khusus, membuatnya menonjol dengan jelas dari gigi normal. Serangan Spesialnya yaitu Kombinasi perisai dan kata-kata yang membuat pengaturan senjata yang cerdas untuk Garuda Eagle yang merupakan fondasi untuk pemutus dalam pedang yang bersinar. Seluruh konstruksi dan desain Garuda Eagle adalah unik. Garuda Eagle tidak memerlukan pengaturan dan roda khusus yang berbeda, karena itu akan membuatnya tidak seimbang. Ini dilengkapi dengan pedang panjang sebagai senjata depan, dan dua perisai di sisinya untuk perlindungan jika terjadi serangan lateral dan bentrokan." ),
            CrushGearTurbo(R.drawable.garuda_phoenix, "Garuda Phoenix", "ガルダフェニックス " + " (Garudafenikkusu)",
                "Garuda Phoenix adalah gigi crush yang dimiliki oleh Kouya Marino di Crush Gear Turbo, dan oleh Crush Kid dan Masaru Mahha di Crush Gear Nitro. Ini adalah salah satu dari sedikit gigi yang muncul di seluruh waralaba crush gear.\n" +
                        "Di Crush Gear Turbo, Garuda Phoenix adalah perlengkapan kedua Kouya setelah penghancuran Garuda Eagle, perlengkapan yang dulunya milik saudara laki -lakinya, Yuhya Marino. Ini adalah salah satu gigi terkuat dari seri dan salah satu dari beberapa gigi yang akan menyaingi Gaiki (yang merupakan gigi yang paling tidak ada duanya pada saat itu) dalam hal kekuatan dan kecepatan selama peluncurannya dan satu -satunya perlengkapan yang telah mengalahkannya. Di Crush Gear Nitro, Garuda Phoenix digunakan oleh Crush Kid, seorang pahlawan super yang sering menyelesaikan perselisihan di antara orang -orang di kota dengan pertempuran.\n" +
                        "Senjata utama Garuda phoenix dilengkapi dengan Paruh pedang trisula bermaterial emas Kemampuan spesial serangannya adalah Vanishing Drive, Double Twister Hammer, Shining Smasher Spin, dan Shining Sword Breaker yang Mematikan" ),
            CrushGearTurbo(R.drawable.raging_bull, "Raging Bull", "レイジングブル " + " (Reijinguburu)",
                "Raging Bull adalah 'crush-gear' yang dimiliki oleh Jirou Oriza dan dibuat oleh Alex Borg.\n" +
                        "\n" +
                        "Raging Bull dibangun oleh Alex sebagai hadiah untuk Jirou untuk bakatnya yang berkembang dalam pertempuran perlengkapan. Ini adalah gigi yang cukup kuat dengan kekuatan gigi crush tipe spin. Gaya Jirou merilis Raging Bull ke cincin crush gear terinspirasi dari keteram\n" +
                        "Desain internal Raging Bull dihancurkan oleh ledakan karena terlalu panas dalam pertandingan, Sasis Raging Bull ditingkatkan ke sasis VT oleh Kyousuke setelah rencana desainnya bocor melalui internet. Selama Piala Asia, perlengkapan ini dilengkapi dengan roda Dash Beetle, satu set roda yang dibuat oleh Kyousuke yang meningkatkan kinerjanya selama pertandingan melawan tim Kemenangan. " ),
            CrushGearTurbo(R.drawable.shooting_mirage, "Shooting Mirage", "シューティングミラージュ " + " (Shūtingumirāju)",
                "Shooting Mirage adalah Crush Gear Milik Kuroudo Marume. Meskipun penembakan Mirage tidak benar -benar dimiliki oleh Kuroudo pada awalnya, ia memiliki arti khusus baginya karena ia memiliki beberapa kenangan indah tentang hal itu. Selama kehidupan Kuroudo di Prancis, Ritchie mengancam akan mengambil alih halaman yang merupakan taman bermain untuk Kuroudo dan teman -temannya. Mereka ditantang untuk bertarung dengan Ritchie, Kuroudo dan teman -temannya membeli Mirage dari seorang dealer yang mengklaim bahwa mereka bertahan dalam insiden Hindenburg dan tenggelamnya Titanic. Perlengkapannya berdebu dan tanpa roda ketika mereka membelinya. Kuroudo dan teman -temannya membersihkan Mirage dan menciptakan satu set roda kayu untuk perlengkapan. Melalui iman yang tak tergoyahkan dari teman -temannya, ia memenangkan pertempuran melawan Ritchie. Kuroudo memutuskan untuk tidak memiliki perlengkapannya untuk dilengkapi dengan sasis VT untuk Piala Asia, karena dia berpikir bahwa perlengkapannya akan lebih baik dengan sasis biasa. Penembakan Mirage bisa mengikuti lawan -lawannya." ),
            CrushGearTurbo(R.drawable.tentakol, "Tentakol", "テンタコル " + " (Tentakoru)",
                "Tentakol adalah perlengkapan naksir yang dimiliki oleh masing -masing saudara Takoyama.\n" +
                        "Penampilan Pertama Takoyaki Batoruroiyaru!\n" +
                        "Nomor masuk JCCA-005-BNO-01TT\n" +
                        "Atribut laut\n" +
                        "Digunakan oleh Takoyama Brothers\n" +
                        "Informasi teknis\n" +
                        "Panjang 179,2 mm\n" +
                        "Luas 102,8 mm\n" +
                        "Tinggi 70,0 mm\n" +
                        "Berat 158 g\n" +
                        "CFRP Bahan Cowl\n" +
                        "Jenis motor CGM-001-N\n" +
                        "Revolusi Motor 15330 RPM\n" +
                        "Octwhip Senjata Utama\n" +
                        "VT Chassis no" ),
            CrushGearTurbo(R.drawable.tiger_flare, "Tiger Flare", "タイガフレア " + " (Taigafurea)",
                "Tiger Flare adalah gigi naksir yang dimiliki oleh Lan Fang, salah satu anggota tim Si Xing Hu Tuan di Crush Gear Turbo. Perlengkapan itu (tidak disengaja) dihancurkan oleh Dino Phalanx setelah hitungan mundur 5 detik di babak 2 pertandingannya melawan Kyousuke Jin. Ini juga muncul dalam episode kesembilan dari naksir Gear Nitro sebagai hadiah dari karakter utama seri ini kepada guru kelas mereka, Ms. Funaki.\n" +
                        "\n" +
                        "Informasi teknis\n" +
                        "Panjang 162,3 mm\n" +
                        "Luas 115,2 mm\n" +
                        "Tinggi 65,9 mm\n" +
                        "Berat 151 g\n" +
                        "CFRP Bahan Cowl\n" +
                        "Jenis motor CGM-001-N\n" +
                        "Revolusi Motor 15330 RPM\n" +
                        "Senjata Utama Huoyanren Seperti Tombak Kapak Lancip yang dapat Memotong dan sebagai Penghancur" ),
            CrushGearTurbo(R.drawable.tigeraid, "Tigeraid", "タイガレイド " + " (Taigareido)",
                "Tigaid adalah perlengkapan naksir yang dimiliki oleh Wang Hu, salah satu anggota tim Si Xing Hu Tuan.\n" +
                        "\n" +
                        "Rincian:\n" +
                        "Penampilan pertama Aratanaru Raibaru Wang Hu!\n" +
                        "Nomor Entri CCGA-005-SHT-041TR\n" +
                        "Gletser Atribut\n" +
                        "Aurora\n" +
                        "Serangan Khusus Huchaobinji (Fighting Tiger Killer Paw)\n" +
                        "Digunakan oleh Wang Hu\n" +
                        "\n" +
                        "Informasi Teknis:\n" +
                        "Panjang 171,8 mm\n" +
                        "Luas 115,2 mm\n" +
                        "Tinggi 65,9 mm\n" +
                        "Berat 152 g\n" +
                        "CFRP Bahan Cowl\n" +
                        "Jenis motor CGM-001-N\n" +
                        "Revolusi Motor 15330 RPM\n" +
                        "Senjata Utama Shuangyaren (Dua Bilah Tombak Siap Menusuk)\n" +
                        "VT Chassis Ya" )
        )

        val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.rv_crush_gear_turbo)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = CrushgearturboAdapter(this, crushgearturbo_List){

            val intent = Intent (this, DetailCGT::class.java)
            intent.putExtra(INTENT_PARCELABLE, it)
            startActivity(intent)
        }






    }
}