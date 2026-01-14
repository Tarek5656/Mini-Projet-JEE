<!DOCTYPE html>
<html>
<head>
    <title>Connexion</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="https://www.w3schools.com/w3css/5/w3.css">
    <link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-teal.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body class="w3-teal" style="height:100vh; display:flex; justify-content:center; align-items:center;">

    <div class="w3-card-4 w3-white w3-padding-32 w3-round-large w3-shadow" style="width:650px;">

        <h2 class="w3-center w3-text-teal">Connexion Admin</h2>

        <c:if test="${not empty sessionScope.erreur}">
            <div class="w3-panel w3-red w3-round w3-center w3-margin-bottom">
                ${erreur}
            </div>
            <c:remove var="erreur" scope="session"/>
        </c:if>

        <form action="login" method="post">
            <p>
                <label><b>Nom d'utilisateur</b></label>
                <input type="text" name="username" class="w3-input w3-border w3-round" required>
            </p>
            <p>
                <label><b>Mot de passe</b></label>
                <input type="password" name="password" class="w3-input w3-border w3-round" required>
            </p>
            <p class="w3-center">
                <button type="submit" class="w3-button w3-teal w3-round w3-hover-shadow" style="width:100%;">Se connecter</button>
            </p>
        </form>

        <p class="w3-center w3-small w3-text-grey">© 2026 Prets Banque MiniProjet</p>
    </div>

</body>
</html>
