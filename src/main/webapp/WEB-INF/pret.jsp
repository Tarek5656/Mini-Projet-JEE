<form method="get" action="pret" class="w3-container w3-card w3-padding">

    <div class="w3-row-padding">
    
    	<div class="w3-third">
            <label>ID Pret</label>
            <input class="w3-input w3-border"
                   type="number"
                   step="1"
                   name="id_pret"
                   value="${param.id_pret}">
        </div>

        <div class="w3-third">
            <label>ID Client</label>
            <input class="w3-input w3-border"
                   type="number"
                   step="1"
                   name="id_client"
                   value="${param.id_client}">
        </div>

        <div class="w3-third">
            <label>Type</label>
            <input class="w3-input w3-border"
                   type="text"
                   name="type"
                   value="${param.type}">
        </div>

    </div>

    <div class="w3-row-padding w3-margin-top">

        <div class="w3-third">
            <label>Remboursement min</label>
            <input class="w3-input w3-border"
                   type="number"
                   step="0.1"
                   name="rembMin"
                   value="${param.rembMin}">
        </div>

        <div class="w3-third">
            <label>Remboursement max</label>
            <input class="w3-input w3-border"
                   type="number"
                   step="0.1"
                   name="rembMax"
                   value="${param.rembMax}">
        </div>

    </div>
    
    <div class="w3-row-padding w3-margin-top">

        <div class="w3-third">
            <label>Durée min</label>
            <input class="w3-input w3-border"
                   type="number"
                   step="1"
                   name="dureeMin"
                   value="${param.dureeMin}">
        </div>

        <div class="w3-third">
            <label>Durée max</label>
            <input class="w3-input w3-border"
                   type="number"
                   step="1"
                   name="dureeMax"
                   value="${param.dureeMax}">
        </div>

    </div>
    
    <div class="w3-row-padding w3-margin-top">

        <div class="w3-third">
            <label>Taux min</label>
            <input class="w3-input w3-border"
                   type="number"
                   step="0.01"
                   name="tauxMin"
                   value="${param.tauxMin}">
        </div>

        <div class="w3-third">
            <label>Taux max</label>
            <input class="w3-input w3-border"
                   type="number"
                   step="0.01"
                   name="tauxMax"
                   value="${param.tauxMax}">
        </div>

    </div>

    <button class="w3-button w3-teal w3-margin-top">
        Rechercher
    </button>

    <button type="button" class="w3-button w3-teal w3-margin-top" 
                onclick="window.location.href='pret'">
     Réinitialiser
     </button>

</form>

<hr/>

<button type="button" class="w3-button w3-teal" 
    onclick="window.location.href='pret_gestion?source=pret'">
    Ajouter un prets</button>

<h2>Liste des prets</h2>
<button type="button" class="w3-button w3-teal w3-margin-top" 
             onclick="window.location.href='pret'">
    Prets Totals </button>
<button type="button" class="w3-button w3-teal w3-margin-top" 
             onclick="window.location.href='pret?action=risque'">
    Prets Risqués </button>
             
     <button type="button" class="w3-button w3-teal w3-margin-top" 
             onclick="window.location.href='pret?action=nonrisque'">
     Prets Non Risqués </button>
<p>Nombre de prets : ${prets.size()}</p>

<table border="1" class="w3-table w3-bordered">
    <tr>
        <th>ID</th>
        <th>Client</th>
        <th>Type</th>
        <th>Remboursement</th>
        <th>Durée</th>
        <th>Taux</th>
        <th>Remboursement Total</th>
        <th>Risque Defaut</th>
        <th>Action</th>
    </tr>

    <c:forEach items="${prets}" var="p">
        <tr style="${p.risque >= 50 ? 'background-color:#f44336;color:white;' : ''}">
            <td>${p.id}</td>
            <td>${p.client.id}</td>
            <td>${p.type}</td>
            <td>${p.remboursement}</td>
            <td>${p.duree}</td>
            <td>${p.taux}</td>
            <td>${p.remboursementTotal}</td>
            <td>${p.risque}</td>
            <td>
            	<button type="button" class="w3-button w3-teal" 
                onclick="window.location.href='pret?action=edit&id=${p.id}&source=pret'">
                Modifier </button>
                <button type="button" class="w3-button w3-teal"
		        onclick="showDeleteModal(${p.id})">
			    Supprimer
				</button>
                <button type="button" class="w3-button w3-teal" 
                onclick="window.location.href='pret?action=byClient&id=${p.client.id}'">
                 Voir client </button>
            </td>
        </tr>
        <div id="deleteModal-${p.id}" class="w3-modal" style="display:none">
		  <div class="w3-modal-content w3-animate-zoom w3-card-4 w3-round">
		
		    <header class="w3-container w3-teal">
		      <h3>Confirmation de suppression</h3>
		    </header>
		
		    <div class="w3-container w3-padding">
		      <p>Voulez-vous vraiment supprimer ce prêt ?</p>
		    </div>
		
		    <footer class="w3-container w3-padding w3-right-align">
		
		      <button class="w3-button w3-teal w3-round"
		              onclick="confirmerSuppression(${p.id})">
		        Supprimer
		      </button>
		      
		      <button class="w3-button w3-light-grey w3-round"
		              onclick="closeDeleteModal(${p.id})">
		        Annuler
		      </button>
		      
		    </footer>
		
		  </div>
		</div>
    </c:forEach>
</table>

<script>
function showDeleteModal(idPret) {
    document.getElementById("deleteModal-" + idPret).style.display = "block";
}

function closeDeleteModal(idPret) {
    document.getElementById("deleteModal-" + idPret).style.display = "none";
}

function confirmerSuppression(idPret) {
    window.location.href = "pret?action=delete&id=" + idPret + "&source=pret";
}
</script>

