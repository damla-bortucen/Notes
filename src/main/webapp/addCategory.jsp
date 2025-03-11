<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Add Category</title>
    <jsp:include page="/meta.jsp"/>
</head>

<body>
<jsp:include page="/header.jsp"/>

<div class="container">
    <h2>Add a New Category</h2>

    <div class="alert alert-danger" style="<%= request.getAttribute("error") != null ? "" : "display: none;" %>">
        <%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %>
    </div>

    <form action="addCategory" method="post">
        <div class="form-group">
            <label for="categoryName">Category Name:</label>
            <input type="text" class="form-control" id="categoryName" name="categoryName" required>
        </div>
        <button type="submit" class="btn btn-primary">Add Category</button>
    </form>
</div>

<jsp:include page="/footer.jsp"/>
</body>
</html>
