package com.fahmi.fahmi_android_jetpack_compose

import android.os.Parcel
import android.os.Parcelable

data class NarutoCharacter(
    val name: String,
    val ninjaId: String,
    val imageUrl: String,
    val description_char: String ,
   val  appearance : String,
    val abilities: String,
    val trivia : String,
): Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(ninjaId)
        dest.writeString(imageUrl)
        dest.writeString(description_char)
        dest.writeString(appearance)
        dest.writeString(abilities)
        dest.writeString(trivia)
    }

    companion object CREATOR : Parcelable.Creator<NarutoCharacter> {
        override fun createFromParcel(parcel: Parcel): NarutoCharacter {
            return NarutoCharacter(
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readString() ?: ""
            )
        }

        override fun newArray(size: Int): Array<NarutoCharacter?> {
            return arrayOfNulls(size)
        }
    }
}




val descriptions = listOf(
    "A powerful ninja with a heart of gold.",
    "A skilled warrior with a dark past.",
    "A talented medic-nin with a fierce spirit.",
    "A legendary ninja with a unique eye technique.",
    "A perverted but wise sage with a gift for jutsu.",
    "A twisted and manipulative villain with a lust for power.",
    "A formidable kunoichi and expert in medical ninjutsu.",
    "A tragic figure consumed by revenge and guilt.",
    "A feared member of the Seven Swordsmen of the Mist.",
    "A god-like figure seeking to bring peace through pain and suffering."
)

val narutoCharacters = listOf(
    NarutoCharacter("Naruto Uzumaki", "0001",
        "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/84dc13b7-a2e7-4b45-83ec-311e72e82900/dd6lhka-b73" +
                "887ec-e4ff-4523-83c6-aff7928e4b52.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxO" +
                "Dg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7InBhdGgiOi" +
                "JcL2ZcLzg0ZGMxM2I3LWEyZTctNGI0NS04M2VjLTMxMWU3MmU4MjkwMFwvZGQ2bGhrYS1iNzM4ODdlYy1lNGZmLTQ1MjMtODNjNi1hZmY3OTI4ZTRiNTI" +
                "ucG5nIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmZpbGUuZG93bmxvYWQiXX0.kHfe3Q5AKptcHMdITc66Cddtv0gQ_WsnVIrcOhR11SE", descriptions[0], "s", "s", "s"),
    NarutoCharacter("Sasuke Uchiha", "0002", "https://4.bp.blogspot.com/-y2N-colpbo8/TuqbTx4xDgI/AAAAA" +
            "AAAAGU/huqxkEeGqjA/s1600/Uchiha_Sasuke_45.png" , descriptions[1],"s", "s", "s"),
    NarutoCharacter("Sakura Haruno", "0003", "https://static.zerochan.net/Haruno.Sakura.full.898508.jpg", descriptions[2],"s", "s", "s"),
    NarutoCharacter("Kakashi Hatake", "0004", "https://3.bp.blogspot.com/-09mTotsCB_0/TV0J3t9TwYI/AAAAAAA" +
            "AB94/6L9dDZvNeY0/s1600/naruto__kakashi_chidori_by_newanimationworld.jpg", descriptions[3],"s", "s", "s"),
    NarutoCharacter("Jiraiya", "0005", "https://tse4.mm.bing.net/th?id=OIP.P3GM60j3UqMXaJ7Q0H75AwHaFj&pid=Api&P=0", descriptions[4],"s", "s", "s"),
    NarutoCharacter("Orochimaru", "0006", "https://2.bp.blogspot.com/-0FXvODd9O0k/WA7aohjUxzI/AAAAAAAAAIc/xFU_nt_DRDwlPw-G9Fu_8Lw_72d88myz" +
            "ACLcB/s1600/orochimaru.jpg", descriptions[5],"s", "s", "s"),
    NarutoCharacter("Tsunade", "0007", "https://static0.srcdn.com/wordpress/wp-content/uploads/2018/12/tsunade2.jpg", descriptions[6],"s", "s", "s"),
    NarutoCharacter("Itachi Uchiha", "0008", "https://4.bp.blogspot.com/-pKKL0iM5tjw/UonG-d_RGGI/AAAAAAAAANs/3OEEr67" +
            "xdn4/s1600/Uchiha_Itachi_Wallpaper_by_0Darkfire0.jpg", descriptions[7],"s", "s", "s"),
    NarutoCharacter("Kisame Hoshigaki", "0009", "https://wallpaperaccess.com/full/2337496.jpg",descriptions[8],"s", "s", "s"),
    NarutoCharacter("Pain Tendo", "0010", "https://wallpaperaccess.com/full/978791.jpg",descriptions[9],"s", "s", "s")
)
