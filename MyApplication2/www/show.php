<?php
ini_set('display_errors',1);
error_reporting(E_ALL);

$dbconn = pg_connect('host='.($_POST["host"]).' dbname='.($_POST["dbname"]).' user='.($_POST["user"]).' port='.($_POST["port"]).' password='.($_POST["password"]))
    or die('Could not connect: ' . pg_last_error($dbconn));

$query = 'SELECT * FROM chtomozhet';
$result = pg_query($query) or die('Ошибка запроса: ' . pg_last_error());

echo "<table>\n";
while ($line = pg_fetch_array($result, null, PGSQL_ASSOC)) {
    echo "\t<tr>\n";
    foreach ($line as $col_value) {
        echo "\t\t<td>$col_value</td>\n";
    }
    echo "\t</tr>\n";
}
echo "</table>\n";

$data=$_POST["myHttpData"];
file_put_contents('myTextFile.txt',$data);

pg_free_result($result);

pg_close($dbconn);
?>