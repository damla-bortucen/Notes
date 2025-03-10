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
    <ul>
        <%
            Folder currentFolder = (Folder) request.getAttribute("currentFolder");

            if (currentFolder != null) {
                // Display subfolders
                Map<String, Folder> subfolders = currentFolder.getSubfolders();
                for (Folder subfolder : subfolders.values()) {
        %>
                    <li><a href="displayIndex?folderId=<%= subfolder.getId() %>"><%= subfolder.getName() %></a></li>
        <%
                }

                // Display notes
                Map<String, Note> notes = currentFolder.getNotes();
                for (Note note : notes.values()) {
        %>
                    <li><a href="displayServlet?noteId=<%= note.getId() %>"><%= note.getName() %></a></li>
        <%
                }
            }
        %>
    </ul>
</div>

<jsp:include page="/footer.jsp"/>
</body>
</html>
