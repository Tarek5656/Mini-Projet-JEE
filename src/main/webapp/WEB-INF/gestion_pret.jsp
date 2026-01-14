<form id="pretForm" action="pret_gestion" method="post">

	<input type="hidden" name="source" value="${source}" />
	
    <!-- ID Pret (important pour éviter le doublon) -->
    <c:if test="${not empty pret or not empty id}">
        <input type="hidden" name="id"
               value="${not empty pret ? pret.id : id}" />
    </c:if>

    ID Client :
	<c:choose>
	    <c:when test="${not empty pret}">
	        <input class="w3-input" type="number" name="id_client"
	               value="${pret.client.id}" readonly required />
	    </c:when>
	    <c:when test="${not empty client and empty pret}">
	    	<input class="w3-input" type="number" name="id_client"
	               value="${client.id}" readonly required />
	    </c:when>
	    <c:otherwise>
	        <input class="w3-input" type="number" name="id_client"
	               value="${idClient}" required />
	    </c:otherwise>
	</c:choose>
	<br/>

    Remboursement :
    <input class="w3-input" type="number" step="0.1" name="remboursement"
           value="${not empty pret ? pret.remboursement : remboursement}" required />
    <br/>

    Durée :
    <input class="w3-input" type="number" name="duree"
           value="${not empty pret ? pret.duree : duree}" required />
    <br/>

	<c:choose>
	<c:when test="${not empty pret}">
        Taux :
        <input class="w3-input" type="number" name="taux"
               value="${pret.taux}" required />
        <br/>
    </c:when>
	<c:otherwise>
        <input type="hidden" name="taux" value="${taux}" />
    </c:otherwise>
	</c:choose>


    Type de prêt :
    <select class="w3-input" name="type">
        <option value="IMMOBILIER"
            ${typePret == 'IMMOBILIER' || pret.type == 'IMMOBILIER' ? 'selected' : ''}>
            Immobilier
        </option>
        <option value="AUTOMOBILE"
            ${typePret == 'AUTOMOBILE' || pret.type == 'AUTOMOBILE' ? 'selected' : ''}>
            Automobile
        </option>
    </select>
    <br/>

    <button class="w3-button w3-teal" type="submit">Enregistrer</button>
    <c:choose>
    <c:when test="${source == 'pret_client'}">
    <button type="button" class="w3-button w3-teal" 
    onclick="window.location.href='pret?action=byClient&id=${not empty pret ? pret.client.id : client.id}'">Annuler</button>
    </c:when>
    <c:otherwise>
    <button type="button" class="w3-button w3-teal" 
    onclick="window.location.href='pret'">Annuler</button>
    </c:otherwise>
    </c:choose>
    
</form>

<c:if test="${showConfirm}">
<div class="w3-modal" style="display:block">
  <div class="w3-modal-content w3-animate-zoom w3-card-4 w3-round">

    <header class="w3-container w3-teal">
      <h3>Risque de défaut</h3>
    </header>

    <div class="w3-container">
      <p>
        Le risque de défaut est :
        <b class="w3-text-red">${risque}%</b>
      </p>
      <p>Voulez-vous continuer ?</p>
    </div>

    <footer class="w3-container w3-padding w3-right-align">
      <button class="w3-button w3-red"
              onclick="window.location.href='pret'">
        Annuler
      </button>

      <button class="w3-button w3-teal"
              onclick="confirmerPret()">
        Continuer
      </button>
    </footer>

  </div>
</div>

<script>
function confirmerPret() {
    let form = document.getElementById("pretForm");

    let input = document.createElement("input");
    input.type = "hidden";
    input.name = "confirmed";
    input.value = "true";
    form.appendChild(input);

    form.submit();
}
</script>
</c:if>
