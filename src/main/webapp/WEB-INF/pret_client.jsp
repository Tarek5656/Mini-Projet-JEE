<h2>Prêts du client</h2>

<ul>ID : ${client.id} </ul>
<ul>Ville : ${client.ville} </ul>
<ul>Code Postal : ${client.code_postal} </ul>
<ul>Revenu : ${client.revenu} </ul>

<h2>Liste des prets</h2>
<p>Nombre de prets : ${prets.size()}</p>

<table border="1" class="w3-table w3-bordered">
    <tr>
        <th>ID</th>
        <th>Type</th>
        <th>Remboursement</th>
        <th>Durée</th>
        <th>Taux</th>
        <th>Remboursement Total</th>
        <th>Risque Defaut</th>
        <th>Actions</th>
    </tr>

    <c:forEach items="${prets}" var="p">
        <tr style="${p.risque >= 50 ? 'background-color:#f44336;color:white;' : ''}">
            <td>${p.id}</td>
            <td>${p.type}</td>
            <td>${p.remboursement}</td>
            <td>${p.duree}</td>
            <td>${p.taux}%</td>
            <td>${p.remboursementTotal}</td>
            <td>${p.risque}</td>
            <td>
                <button type="button" class="w3-button w3-teal" 
                onclick="window.location.href='pret?action=edit&id=${p.id}&source=pret_client'">
                Modifier </button>
                <button type="button" class="w3-button w3-teal"
		        onclick="showDeleteModal(${p.id})">
			    Supprimer
				</button>
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

<br>

	<button type="button" class="w3-button w3-teal" 
	onclick="window.location.href='pret_gestion?action=client&id=${client.id}&source=pret_client'">
	Ajouter un pret </button>
	<button type="button" class="w3-button w3-teal" 
	onclick="window.location.href='client'">
	Retour clients </button>
	<button type="button" class="w3-button w3-teal" 
	onclick="window.location.href='pret'">
	Retour prets </button>

<script>
function showDeleteModal(idPret) {
    document.getElementById("deleteModal-" + idPret).style.display = "block";
}

function closeDeleteModal(idPret) {
    document.getElementById("deleteModal-" + idPret).style.display = "none";
}

function confirmerSuppression(idPret) {
    window.location.href = "pret?action=delete&id=" + idPret + "&source=pret_client";
}
</script>




