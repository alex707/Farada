<?php
ini_set('display_errors',1);
error_reporting(E_ALL);

$dbconn = pg_connect('host='.($_POST["host"]).' dbname='.($_POST["dbname"]).' user='.($_POST["user"]).' port='.($_POST["port"]).' password='.($_POST["password"]))
    or die('Could not connect: ' . pg_last_error($dbconn));

$query = "INSERT INTO chtomozhet VALUES ('".($_POST["bultihatsa"])."','".($_POST["lomatzabor"])."')";
$result = pg_query($query) or die('Ошибка запроса: ' . pg_last_error($query));

pg_free_result($result);

pg_close($dbconn);
echo "ok";
?>