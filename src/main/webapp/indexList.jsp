<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="ucl.ac.uk.classes.Folder" %>
<%@ page import="ucl.ac.uk.classes.Note" %>

<html>
<head>
    <title>Note Taking App</title>
    <jsp:include page="/meta.jsp"/>
    <style>
        .scrollable-list {
            max-height: 25vh; /*25% of the page*/
            overflow-y: auto; /*scrollable if too many notes*/
        }
        .button-container {
            display: inline-flex;
            gap: 10px; /* Adjust spacing */
            align-items: center;
        }
    </style>
</head>


<body>
<jsp:include page="/header.jsp"/>

<div class="container">
    <%
        String title = (String) request.getAttribute("title");
        Folder currentFolder = (Folder) request.getAttribute("currentFolder");
    %>

    <h1><%= title %></h1>

    <form method="get" action="displayIndex">

        <input type="hidden" name="folderId" value="<%= currentFolder.getId() %>">

        <label for="sort">Sort By:</label>
        <select class="form-select" name="sort" id="sort">
            <option value="alpha">Alphabetical</option>
            <option value="newest">Newest to Oldest</option>
            <option value="oldest">Oldest to Newest</option>
        </select>
        <button type="submit" class="btn btn-primary btn-sm">Sort</button>
    </form>

    <h3>Folders</h3>
    <ul class="list-group scrollable-list">
        <%
            if (currentFolder != null) {
                // Display subfolders
                List<Folder> sortedSubfolders = (List<Folder>) request.getAttribute("sortedFolders");
                if (!sortedSubfolders.isEmpty()) {
                    for (Folder subfolder : sortedSubfolders) {
        %>
                        <li class="list-group-item">
                            <p><a href="displayIndex?folderId=<%= subfolder.getId() %>"><%= subfolder.getName() %></a></p>
                            <p>Created: <%= subfolder.getDateTime() %></p>

                            <form action="deleteFolder" method="post" style="display:inline;">
                                <input type="hidden" name="folderId" value="<%= subfolder.getId() %>">
                                <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                            </form>
                        </li>
        <%          }
                } else {
        %>
                    <p>This folder does not have subfolders</p>
        <%      } %>
    </ul>

    <h3>Notes</h3>
    <ul class="list-group scrollable-list"">
        <%
                // Display notes
                List<Note> sortedNotes = (List<Note>) request.getAttribute("sortedNotes");
                if (!sortedNotes.isEmpty()) {
                    for (Note note : sortedNotes) {
        %>
                        <li class="list-group-item">
                            <p><a href="displayNote?noteId=<%= note.getId() %>"><%= note.getName() %></a></p>
                            <p>Created: <%= note.getDateTime() %></p>

                            <form action="editNote" method="post" style="display:inline;">
                                <input type="hidden" name="noteId" value="<%= note.getId() %>">
                                <button type="submit" class="btn btn-info btn-sm">Edit</button>
                            </form>

                            <form action="deleteNote" method="post" style="display:inline;">
                                <input type="hidden" name="noteId" value="<%= note.getId() %>">
                                <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                            </form>
                        </li>
        <%          }
                } else {
        %>
                    <p>This folder does not have notes</p>
        <%
                }
            }
        %>
    </ul>

    <div class="button-container">
        <div class="dropdown">
            <button class="btn btn-success" type="button" data-toggle="dropdown">
                <span class="glyphicon glyphicon-plus"></span> Add
            </button>
            <ul class="dropdown-menu">
                <li><a href="addItem?folderId=<%= currentFolder.getId() %>&itemType=note">Add Note</a></li>
                <li><a href="addItem?folderId=<%= currentFolder.getId() %>&itemType=folder">Add Folder</a></li>
            </ul>
        </div>

        <%
            if (currentFolder.getParentId() != null) {
        %>
                <a href="displayIndex?folderId=<%=currentFolder.getParentId()%>" class="btn btn-primary">Back</a>
        <%  } %>
    </div>
</div>

<jsp:include page="/footer.jsp"/>
</body>
</html>
