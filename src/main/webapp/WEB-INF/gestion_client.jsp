<h2>Ajouter / Modifier un client</h2>

<form action="client_gestion" method="post">

	<c:choose>
	<c:when test="${not empty client}">
	ID Client :
    	<input class="w3-input" type="number" name="id" value="${client.id}" readonly />
    	<br/>
	</c:when>
	</c:choose>

    Ville :
    <input class="w3-input" type="text"
    	   name="ville" value="${client.ville}" required />
    <br/>
    
    Code Postal :
    <input class="w3-input" type="number" step="1"
    	   name="code_postal" value="${client.code_postal}" required />

    <br/>

    Revenu :
    <input class="w3-input" type="number" step="0.1"
           name="revenu" value="${client.revenu}" required />

    <br/>
    <button class="w3-button w3-teal" type="submit">Enregistrer</button>
    <button type="button" class="w3-button w3-teal" onclick="window.location.href='client'">Annuler</button>
</form>