<%@ page import="java.util.Map" %>
<%@ page import="ucl.ac.uk.main.Folder" %>
<%@ page import="ucl.ac.uk.main.Note" %>

<html>
<head>
    <title>Note Taking App</title>
    <jsp:include page="/meta.jsp"/>

</head>


<body>
<jsp:include page="/header.jsp"/>

<div class="container">
    <h1>Folders and Notes</h1>
    <ul class="list-group">
        <%
            Folder currentFolder = (Folder) request.getAttribute("currentFolder");

            if (currentFolder != null) {
                // Display subfolders
                Map<String, Folder> subfolders = currentFolder.getSubfolders();
                for (Folder subfolder : subfolders.values()) {
        %>
                    <li class="list-group-item">
                        <a href="displayIndex?folderId=<%= subfolder.getId() %>"><%= subfolder.getName() %></a>
                    </li>
        <%
                }

                // Display notes
                Map<String, Note> notes = currentFolder.getNotes();
                for (Note note : notes.values()) {
        %>
                    <li class="list-group-item">
                        <p><a href="displayNote?noteId=<%= note.getId() %>"><%= note.getName() %></a></p>
                        <p><%= note.getDateTime() %></p>
                    </li>
        <%
                }
            }
        %>
    </ul>

    <div class="dropdown">
        <button class="btn btn-success" type="button" data-toggle="dropdown">
            <span class="glyphicon glyphicon-plus"></span> Add
        </button>
        <ul class="dropdown-menu">
            <li><a href="addItem?folderId=<%= currentFolder.getId() %>&itemType=note">Add Note</a></li>
            <li><a href="addItem?folderId=<%= currentFolder.getId() %>&itemType=folder">Add Folder</a></li>
        </ul>
    </div>

</div>

<jsp:include page="/footer.jsp"/>
</body>
</html>
