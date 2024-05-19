# Project Proposal

## Introduction

<ol type='a'>
  <li><b>Project Title</b></li><br>
  <p>Aplikasi Pencarian Tempat Makan (<b>Eatera</b>)</p><br>
  
  <li><b>Short Description</b></li><br>
  <p>Eatera merupakan aplikasi pencarian masakan/tempat makan yang memudahkan pengguna untuk menemukan pilihan makanan        terbaik di berbagai tempat khususnya di sekitar kampus     ITERA. Dengan fitur pencarian canggih dan rekomendasi            personal, pengguna dapat dengan mudah menemukan tempat makan berdasarkan lokasi, jenis masakan, jarak, dan rating.          Eatera juga memungkinkan pengguna untuk melihat ulasan, menu, dan informasi lainnya yang berkaitan tentang tempat makan     yang ingin dilihat. Kemudian pengguna dapat menyimpan   daftar tempat makanan yang disukai ke daftar favorit. </p><br>
  
  <li><b>Project Team</b></li><br>
</ol>

| Nama         | NIM                   |
|-------------|------------------------|
| Rizki Esa Fadillah  | 121140084       |
| Fandu Dipo Anderson  | 121140033       |
| Dimas Saputra   | 121140059       |
| Adriel Gideon Benedict Sirait  | 121140196       |
| Natanael Argajova  | 121140205       |


## Proposed Application

<ol type='a'>
  <li><b>Application Description</b></li><br>
  <p>Aplikasi ini bertujuan untuk membantu pengguna menemukan tempat makan terdekat berdasarkan preferensi pengguna.<br><br>
  Halaman Beranda: Halaman ini adalah pusat informasi utama dalam Eatera. Di sini, pengguna akan menemukan pilihan restoran   dan masakan yang disesuaikan dengan preferensi mereka. Pengguna juga dapat menjelajahi restoran dan masakan lainnya         dengan menggunakan fitur pencarian atau menavigasi melalui kategori yang tersedia.<br><br>
  Halaman Detail Makanan: Halaman ini memberikan informasi lengkap tentang tempat makan tertentu. Ini mencakup nama tempat,   alamat, jam operasional, deskripsi singkat, ulasan, rating, dan lain - lain. Pengguna dapat melihat foto-foto tempat        makan,   membaca ulasan dari pengguna lain serta menemukan petunjuk arah menggunakan peta interaktif. Halaman ini juga      menyediakan   tombol seperti "Tambahkan ke Favorit" untuk memudahkan pengguna untuk mengingat tempat makan tersebut.<br>    <br>
  Halaman Profil: Halaman profil adalah tempat di mana pengguna dapat melihat dan mengubah informasi pribadi. Ini mencakup    foto profil, data diri pengguna, dan bookmark tempat makan favorit. 
  </p><br>
  
  <li><b>Application Advantages</b></li><br>
  <p>Aplikasi Eatera memiliki beberapa kelebihan yang membuatnya menjadi solusi ideal bagi para penjelajah kuliner.           Pengguna dapat menikmati pilihan makanan yang disesuaikan dengan preferensi mereka. Dengan UI yang intuitif memastikan      akses yang mudah ke informasi detail tentang setiap tempat makan, termasuk menu, dan rating pengguna. Integrasi dengan      peta interaktif membantu pengguna menemukan lokasi tempat makan dengan mudah. Dengan kemampuan untuk menyimpan              masakan/tempat makan favorit dan memberikan rating, aplikasi ini juga memungkinkan pengguna untuk berkontribusi dalam       membangun komunitas kuliner yang aktif di sekitar kampus. </p><br>
  
  <li><b>Application Benefits</b></li><br>
  <p>Dengan menggunakan Eatera pengguna dapat menghemat waktu dan usaha dalam mencari tempat makan yang sesuai dengan         keinginan mereka. Pengguna juga dapat menjelajahi dan menikmati berbagai kuliner baru di sekitar ITERA, dan menjelajahi     dunia kuliner.</p><br>
</ol>

## System Planning

<ol type='a'>
  <li><b>Application Architecture</b></li><br>
  <p>Aplikasi Eatera menggunakan arsitektur MVVM (Model-View-ViewModel) sebagai dasar aplikasinya. Dalam arsitektur ini,      aplikasi terbagi menjadi tiga bagian utama: </p><br>
  
  <ul type='disc'>
    <li><i>Model</i>: Bagian ini bertanggung jawab atas logika bisnis dan data aplikasi. Model mewakili sumber daya data        seperti tempat-tempat makan dan menyediakan operasi untuk mengambil, menyimpan, dan memperbarui data. Ini juga dapat        mencakup integrasi dengan layanan pihak ketiga, seperti Google Places API, untuk mengambil informasi tempat makan. </li>
    <br>
    <li><i>View</i>: Bagian ini adalah antarmuka pengguna (UI) yang dilihat dan digunakan oleh pengguna. View hanya             menampilkan informasi dan mengirim perintah ke ViewModel. Dalam konteks aplikasi ini, View akan menampilkan daftar          tempat makan, detail tempat makan, kolom pencarian, dan lainnya. </li>
    <br>
    <li><i>ViewModel</i>: Bagian ini bertindak sebagai perantara antara Model dan View. ViewModel bertanggung jawab untuk       mempersiapkan dan menyajikan data dari Model ke View, serta menangani interaksi pengguna dan logika tampilan. Misalnya,     ViewModel akan mengambil daftar tempat makan dari Model dan menyiapkannya untuk ditampilkan di View. Ini juga akan          merespons perubahan yang dilakukan pengguna di antarmuka pengguna, seperti ketika pengguna memasukkan kriteria              pencarian, dan memperbarui Model sesuai dengan itu. </li>
  </ul><br>
  
  <p>Dengan menggunakan arsitektur MVVM, Eatera memisahkan peran antara logika aplikasi dan tampilan pengguna, sehingga       memudahkan pengembangan tim yang terdiri dari desainer dan pengembang. Tim desainer dapat fokus pada desain antarmuka       pengguna tanpa perlu memperhatikan logika aplikasi, sementara tim pengembang dapat berkonsentrasi pada implementasi         logika aplikasi tanpa harus terganggu oleh tampilan pengguna. Hal ini meningkatkan efisiensi pengembangan aplikasi dan      memungkinkan kolaborasi yang lebih baik antara anggota tim.	</p><br
  
  <li><b>Entity Relationship Diagram (ERD)</b></li><br><br>
  <img src="https://github.com/121140084-Rizki-Esa-Fadillah/Tugas_Besar_PAM/blob/main/Image/ERD_PAM.png"
    width = "750"
    height = "350"><br>
  <p>Dari diagram ER di atas, ada empat entitas utama yaitu "Tempat Makan", "Daftar Favorit", "User", dan "Detail Tempat      Makan". Setiap entitas memiliki atributnya sendiri. "user" dalam hubungannya dengan "Tempat Makan" dan "Daftar Favorit"     adalah "Akses", sementara "Tempat Makan" dan “Daftar Favorit” dalam hubungannya dengan "Detail Tempat Makan" adalah         "Memiliki".</p><br>
  
  <li><b>UML Design</b></li><br>
  <ul type='disc'>
    <li><i>Activity Diagram</i></li><br>
      <ol type='1'>
        <li><b>Login</b></li><br>
          <img src="https://github.com/121140084-Rizki-Esa-Fadillah/Tugas_Besar_PAM/blob/master/Image/Activity_Diagram_Login.png">
        <li><b>Register</b></li><br>
          <img src="https://github.com/121140084-Rizki-Esa-Fadillah/Tugas_Besar_PAM/blob/master/Image/Activity_Diagram_Register.png">
        <li><b>Search</b></li><br>
          <img src="https://github.com/121140084-Rizki-Esa-Fadillah/Tugas_Besar_PAM/blob/master/Image/Activity_Diagram_Search.png">
        <li><b>Menambahkan Tempat Makan Favorit</b></li><br>
          <img src="https://github.com/121140084-Rizki-Esa-Fadillah/Tugas_Besar_PAM/blob/master/Image/Activity_Diagram_Add_Favorite.png">
        <li><b>Menghapus Tempat Makan Favorit</b></li><br>
          <img src="https://github.com/121140084-Rizki-Esa-Fadillah/Tugas_Besar_PAM/blob/master/Image/Activity_Diagram_Delete_Favorite.png">
        <li><b>Edit profil</b></li><br>
          <img src="https://github.com/121140084-Rizki-Esa-Fadillah/Tugas_Besar_PAM/blob/master/Image/Activity_Diagram_Edit_Profil.png">
        <li><b>Logout</b></li><br>
          <img src="https://github.com/121140084-Rizki-Esa-Fadillah/Tugas_Besar_PAM/blob/master/Image/Activity_Diagram_Logout.png">
      </ol><br>
    <li><i>Sequence Diagram</i></li><br>
      <ol type='1'>
        <li><b>Login Sequence</b></li><br>
          <img src="https://github.com/121140084-Rizki-Esa-Fadillah/Tugas_Besar_PAM/blob/master/Image/Login.png">
        <li><b>Register Sequence</b></li><br>
          <img src="https://github.com/121140084-Rizki-Esa-Fadillah/Tugas_Besar_PAM/blob/master/Image/Register.png">
        <li><b>Search Sequence</b></li><br>
          <img src="https://github.com/121140084-Rizki-Esa-Fadillah/Tugas_Besar_PAM/blob/master/Image/Search.png">
        <li><b>Add Favorite Sequence</b></li><br>
          <img src="https://github.com/121140084-Rizki-Esa-Fadillah/Tugas_Besar_PAM/blob/master/Image/Add_Favorite.png">
        <li><b>Delete Favorite Sequence</b></li><br>
          <img src="https://github.com/121140084-Rizki-Esa-Fadillah/Tugas_Besar_PAM/blob/master/Image/Delete_Favorite.png">
        <li><b>Edit Profile Sequence</b></li><br>
          <img src="https://github.com/121140084-Rizki-Esa-Fadillah/Tugas_Besar_PAM/blob/master/Image/Edit_Profile.png">
        <li><b>Logout Sequence</b></li><br>
          <img src="https://github.com/121140084-Rizki-Esa-Fadillah/Tugas_Besar_PAM/blob/master/Image/Logout.png">
      </ol>
  </ul><br>

  <li><b>Class Diagram</b></li><br>
  <img src="https://github.com/121140084-Rizki-Esa-Fadillah/Tugas_Besar_PAM/blob/master/Image/Class_Diagram_PAM.png"
    width = "750"
    height = "750"><br>
  <p>
    Dalam Class Diagram ini terdapat 6 kelas, antara lain : User, Tempat Makan, Search, Profil, List, dan Favorite List.      </p>
  <ol type='1'>
    <li>Kelas User memiliki lima atribut antara lain atribut Nama, Jenis Kelamin, Usia, Email dan Password. Dengan              menggunakan atribut-atribut ini, kita dapat menjalankan 2 metode, yaitu login atau register.</li><br>
    <li>Kelas Tempat Makan memiliki atribut-atribut yang digunakan untuk menjelaskan detail informasi seputar tempat makan      yang sedang dilihat, mulai dari Nama, Jarak, Rating, Jenis Masakan, Alamat, Jam Operasional, Menu Makanan, dan Ulasan.      Dengan atribut-atribut tersebut, akan ada 2 metode yang dapat dijalankan: <br>
    a. Search_tempat_makan: untuk mencari tempat makan sesuai preferensi user.<br>
    b. Add_to_favorite: untuk menambahkan tempat makan tersebut ke daftar favorit user. 
    </li><br>
    <li>Kelas Search akan digunakan untuk menjalankan 4 fungsi antara lain: search_by_nama (berdasarkan nama tempat makan),     search_by_jenis_masakan (berdasarkan jenis masakan), search_by_rating (berdasarkan rating pelanggan), dan                   search_by_jarak (berdasarkan jarak antara pengguna dengan tempat makanan). Fungsi - fungsi ini dapat digunakan untuk        mencari tempat makan yang sesuai dengan preferensi user dengan menggunakan kolom pencarian pada aplikasi. </li><br>
    <li>Kelas List akan berisi semua tempat makan yang telah ditambahkan oleh user ke daftar favorite. Kelas ini akan           berisi beberapa atribut tentang tempat makan yang telah ditambahkan ke dalam daftar favorit, seperti nama,jenis             masakan, jarak dan rating. Dan user dapat menggunakan atribut ini untuk mencari tempat makan tersebut di dalam daftar       (search_list) atau untuk menghapusnya dari daftar (delete_from_list). Kelas ini juga memiliki kelas turunan yaitu           Favorite List. </li><br>
    <li>Kelas Profile akan terdiri dari beberapa atribut tentang pengguna, seperti Nama, Jenis Kelamin, Usia, Email dan         Password. Dalam kelas ini pengguna dapat menjalankan beberapa metode seperti: <br>
    a. Edit_profile: untuk mengubah data profil user.<br>
    b. See_favorite_list: untuk masuk ke halaman daftar favorit.<br>
    c. Logout: untuk keluar dari akun kita.
    </li>
  </ol><br>
  
  <li><b>UI Design (Figma)</b></li><br>
  <ol type='1'>
    <li>(https://www.figma.com/design/WF0rkLw5Ki6mkdIJ7UXVwA/Eatera?node-id=0-1&t=CY4Han9R907hGjQu-0)</li>
    <li></li>
  </ol><br>
</ol>

## Application Development Stage

<ol type='a'>
  <li><b>Implementation Stage</b></li><br>
  <p>Pada pembuatan aplikasi Eatera, kami menggunakan metode waterfall yang dijabarkan dalam berbagai tahap berikut:<br><br>
  Perencanaan: Pertama kami akan melakukan analisis kebutuhan pengguna, hasil dari tahap ini adalah dokumen spesifikasi       yang mencakup semua fitur dan fungsionalitas yang dibutuhkan oleh pengguna. Fitur utama yang akan diperlihatkan seperti     pencarian masakan sesuai preferensi, menu, daftar favorit, serta ulasan pengguna.<br><br>
  Desain:  Berdasarkan spesifikasi kebutuhan yang kami dapatkan, kami akan menciptakan desain UI/UX menggunakan figma,        tertata agar desain mudah dan dinamis digunakan pengguna. <br><br>
  Pengembangan program: Disini kami akan menggunakan arsitektur MVVM, program akan dibuat sesuai spesifikasi desain.<br><br>
  Pengujian: Setelah pengembangan selesai, pengujian yang ketat dilakukan untuk memastikan bahwa semua fitur berfungsi        dengan benar. Ini termasuk pengujian Unit menggunakan Mockito dengan Fake, Mock, dan Dummy, serta Pengujian UI alur         dengan API menggunakan MockWebServer. <br><br>
  Pengimplementasian : Setelah pengujian berhasil, aplikasi diimplementasikan dan dibuat tersedia untuk pengguna. Ini         termasuk pembuatan APK untuk aplikasi.
  </p><br>
  
  <li><b>Time Plan</b></li><br>
  <p>Proyek dimulai pada tanggal 11 April 2024 dan akan berakhir pada tanggal 23 Mei 2024. Pengembangan Aplikasi Eatera       akan dimulai pada tanggal 25 April 2024 dan diperkirakan akan memakan waktu sekitar 5 minggu untuk diselesaikan.	
  </p><br>
  <img src="https://github.com/121140084-Rizki-Esa-Fadillah/Tugas_Besar_PAM/blob/main/Image/Timeline_UAS_PAM.png"
    width = "750"
    height = "350">
</ol>
