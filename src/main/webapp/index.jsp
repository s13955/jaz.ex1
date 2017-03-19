<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Zadanie zaliczeniowe 1</title>
    <link rel="stylesheet" href="/css/app.css">
</head>
<body>

    <header class="site-header">
        <div class="vertical">
            <h1 class="page-title"><a href="/">Symulacja</a></h1>
        </div>
    </header>

    <main class="content" role="main">
        <form class="simulation-form" method="post" action="/generate">
            <div>
                <label for="capital">Wnioskowana kwota kredytu</label>
                <input type="number" name="capital" id="capital" min="0" value="100000">
            </div>
            <div>
                <label for="instalments">Ilość rat</label>
                <input type="number" name="instalments" id="instalments" min="1" value="144">
            </div>
            <div>
                <label for="interestRate">Oprocentowanie</label>
                <input type="number" name="interestRate" id="interestRate" min="0" max="100" step="0.1" value="3.5">
            </div>
            <div>
                <label for="fixedCharge">Opłaty stałe</label>
                <input type="number" name="fixedCharge" id="fixedCharge" min="0" value="0">
            </div>
            <div>
                <label>Rodzaj rat</label>
                <label><input type="radio" name="type" value="EQUAL" checked="checked"> równe</label>
                <label><input type="radio" name="type" value="DECREASING"> malejące</label>
            </div>


            <button class="button" type="submit">Pokaż symulację</button>
            <button class="button" type="submit" formmethod="get">Pobierz PDF</button>
        </form>
    </main>

    <footer class="site-footer clearfix">
        Copyright &copy; 2017
    </footer>

</body>
</html>
