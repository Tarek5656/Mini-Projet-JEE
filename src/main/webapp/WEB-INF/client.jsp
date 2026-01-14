<form method="get" action="client" class="w3-container w3-card w3-padding">

    <div class="w3-row-padding">
    
    	<div class="w3-third">
            <label>ID Client</label>
            <input class="w3-input w3-border"
                   type="number"
                   step="1"
                   name="id"
                   value="${param.id}">
        </div>

        <div class="w3-third">
            <label>Ville</label>
            <input class="w3-input w3-border"
                   type="text"
                   name="ville"
                   value="${param.ville}">
        </div>

        <div class="w3-third">
            <label>Code postal</label>
            <input class="w3-input w3-border"
                   type="text"
                   name="code_postal"
                   value="${param.code_postal}">
        </div>

    </div>

    <div class="w3-row-padding w3-margin-top">

        <div class="w3-third">
            <label>Revenu min</label>
            <input class="w3-input w3-border"
                   type="number"
                   step="0.1"
                   name="revenuMin"
                   value="${param.revenuMin}">
        </div>

        <div class="w3-third">
            <label>Revenu max</label>
            <input class="w3-input w3-border"
                   type="number"
                   step="0.1"
                   name="revenuMax"
                   value="${param.revenuMax}">
        </div>

    </div>

    <button class="w3-button w3-teal w3-margin-top">
        Rechercher
    </button>

    <button type="button" class="w3-button w3-teal w3-margin-top" 
                onclick="window.location.href='client'">
     Réinitialiser
     </button>

</form>


<hr/>
    <button type="button" class="w3-button w3-teal" 
    onclick="window.location.href='client_gestion'">
    Ajouter un client</button>

<h2>Liste des clients</h2>
<p>Nombre de clients : ${clients.size()}</p>

<table border="1" class="w3-table w3-bordered">
    <tr>
        <th>ID</th>
        <th>Ville</th>
        <th>Code postal</th>
        <th>Revenu</th>
        <th>Actions</th>
    </tr>

    <c:forEach items="${clients}" var="c">
        <tr>
            <td>${c.id}</td>
            <td>${c.ville}</td>
            <td>${c.code_postal}</td>
            <td>${c.revenu}</td>
            <td>
                <button type="button" class="w3-button w3-teal" 
                onclick="window.location.href='client?action=edit&id=${c.id}'">
                Modifier </button>
                <button type="button" class="w3-button w3-teal"
				onclick="showDeleteModal(${c.id})">		    
				Supprimer
				</button>
                <button type="button" class="w3-button w3-teal" 
                onclick="window.location.href='pret?action=byClient&id=${c.id}'">
                Voir prêts </button>
            </td>
        </tr>
        <div id="deleteModal-${c.id}" class="w3-modal" style="display:none">
		  <div class="w3-modal-content w3-animate-zoom w3-card-4 w3-round">
		
		    <header class="w3-container w3-teal">
		      <h3>Confirmation de suppression</h3>
		    </header>
		
		    <div class="w3-container w3-padding">
		      <p>Voulez-vous vraiment supprimer ce client ?</p>
		    </div>
		
		    <footer class="w3-container w3-padding w3-right-align">
		
		      <button class="w3-button w3-teal w3-round"
		              onclick="confirmerSuppression(${c.id})">
		        Supprimer
		      </button>
		      
		      <button class="w3-button w3-light-grey w3-round"
		              onclick="closeDeleteModal(${c.id})">
		        Annuler
		      </button>
		      
		    </footer>
		
		  </div>
		</div>
    </c:forEach>
</table>

<script>
function showDeleteModal(idClient) {
    document.getElementById("deleteModal-" + idClient).style.display = "block";
}

function closeDeleteModal(idClient) {
    document.getElementById("deleteModal-" + idClient).style.display = "none";
}

function confirmerSuppression(idClient) {
    window.location.href = "client?action=delete&id=" + idClient;
}
</script>
