<?php
  include 'koneksi.php';

  $post = json_decode(file_get_contents("php://input"),true);
  $like = $post['parambarcodedariandroid'];

  $query = mysqli_query ($koneksi, "SELECT * FROM tb_barang where kd_barang='$like'");

  $cekbaris = mysqli_num_rows ($query) ;

  $temparray = array();
  if ($cekbaris > 0) {
      while ($rows = mysqli_fetch_assoc($query)) {

            $temparray[]= $rows;
      }

  }

  echo json_encode(array("databarang"=>$temparray));



 ?>
