<%@ page import="ucl.ac.uk.main.Folder" %>

<html>
<head>
    <title>Add Folder</title>
    <jsp:include page="/meta.jsp"/>
</head>

<body>
<jsp:include page="/header.jsp"/>

<div class="container">
    <h2>Add New Folder</h2>

    <form action="saveFolder" method="post">
        <!-- Pass the parent folder ID to the servlet -->
        <input type="hidden" name="parentFolderId" value="<%= request.getAttribute("folderId") %>">

        <div class="form-group">
            <label for="folderName">Folder Name:</label>
            <input type="text" id="folderName" name="folderName" class="form-control" required>
        </div>

        <button type="submit" class="btn btn-primary">Save Folder</button>
    </form>
</div>

<jsp:include page="/footer.jsp"/>
</body>
</html>
