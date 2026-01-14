<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Prêts Bancaires</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="https://www.w3schools.com/w3css/5/w3.css">
    <link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-teal.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>

<body>

<jsp:include page="sidebar.jsp" />

<div class="w3-main" style="margin-left:130px;"> 

    <jsp:include page="header.jsp" />

    <!-- CONTENU DYNAMIQUE -->
    <div class="w3-container w3-padding-large w3-section w3-light-grey">
    
    	<c:if test="${not empty sessionScope.success}">
		    <div class="w3-panel w3-green w3-round">
		        ${sessionScope.success}
		    </div>
		    <c:remove var="success" scope="session"/>
		</c:if>
		
		<c:if test="${not empty sessionScope.erreur}">
		    <div class="w3-panel w3-red w3-round">
		        ${sessionScope.erreur}
		    </div>
		    <c:remove var="erreur" scope="session"/>
		</c:if>
    	
        <jsp:include page="${content}" />
    </div>

    <jsp:include page="footer.jsp" />

</div>

<script>
let sortDirection = {}; // garde la direction pour chaque colonne

function sortTable(n) {
    let table = document.getElementById("clientsTable");
    let rows = Array.from(table.rows).slice(1); // exclure le header
    let direction = sortDirection[n] === "asc" ? "desc" : "asc";
    
    rows.sort((rowA, rowB) => {
        let cellA = rowA.cells[n].innerText;
        let cellB = rowB.cells[n].innerText;

        // si c'est un nombre, comparer numériquement
        let numA = parseFloat(cellA);
        let numB = parseFloat(cellB);
        if (!isNaN(numA) && !isNaN(numB)) {
            return direction === "asc" ? numA - numB : numB - numA;
        }

        // sinon comparer en chaîne de caractères
        return direction === "asc"
            ? cellA.localeCompare(cellB)
            : cellB.localeCompare(cellA);
    });

    // ré-attacher les lignes triées au tableau
    for (let row of rows) {
        table.appendChild(row);
    }

    // mémoriser la direction pour la prochaine fois
    sortDirection[n] = direction;
}
</script>

</body>
</html>
