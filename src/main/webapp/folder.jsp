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
        .container {
            padding-bottom: 60px; /* enough space for the fixed footer */
        }
        .dropdown-right .dropdown-menu {
            top: 0;
            left: 100%;
            margin-left: 0.25rem;
            position: absolute;
        }
    </style>
</head>


<body>
<jsp:include page="/header.jsp"/>

<div class="container">
    <%
        String title = (String) request.getAttribute("title");
        Folder currentFolder = (Folder) request.getAttribute("currentFolder");
        String sort = (String) request.getAttribute("sort");
    %>

    <h1><%= title %></h1>
    <%
        if (currentFolder.getParentId() != null) {
    %>
        <form action="editFolder" method="get">
            <input type="hidden" name="folderId" value="<%= currentFolder.getId() %>">
            <button type="submit" class="btn btn-info btn-sm">
                <span class="glyphicon glyphicon-pencil"></span> Edit Folder
            </button>
        </form>
    <% } %>

    <form method="get" action="displayFolder">

        <input type="hidden" name="folderId" value="<%= currentFolder.getId() %>">

        <label for="sort">Sort By:</label>
        <select class="form-select" name="sort" id="sort">
            <option value="alpha" <%= "alpha".equals(sort) ? "selected" : "" %>>Alphabetical</option>
            <option value="newest" <%= "newest".equals(sort) ? "selected" : "" %>>Newest to Oldest</option>
            <option value="oldest" <%= "oldest".equals(sort) ? "selected" : "" %>>Oldest to Newest</option>
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
                            <p><a href="displayFolder?folderId=<%= subfolder.getId() %>"><%= subfolder.getName() %></a></p>
                            <p>Created: <%= subfolder.getDateTime() %></p>

                            <form action="deleteFolder" method="post" style="display:inline;">
                                <input type="hidden" name="folderId" value="<%= subfolder.getId() %>">
                                <button type="submit" class="btn btn-danger btn-sm">
                                    <span class="glyphicon glyphicon-trash"></span> Delete
                                </button>
                            </form>
                        </li>
        <%          }
                } else {
        %>
                    <p>This folder does not have subfolders</p>
        <%      } %>
    </ul>

    <h3>Notes</h3>
    <ul class="list-group scrollable-list">
        <%
                // Display notes
                List<Note> sortedNotes = (List<Note>) request.getAttribute("sortedNotes");
                if (!sortedNotes.isEmpty()) {
                    for (Note note : sortedNotes) {
        %>
                        <li class="list-group-item">
                            <p><a href="displayNote?noteId=<%= note.getId() %>"><%= note.getName() %></a></p>
                            <p>Created: <%= note.getDateTime() %></p>

                            <form action="editNote" method="get" style="display:inline;">
                                <input type="hidden" name="noteId" value="<%= note.getId() %>">
                                <button type="submit" class="btn btn-info btn-sm">
                                    <span class="glyphicon glyphicon-pencil"></span> Edit
                                </button>
                            </form>

                            <form action="deleteNote" method="post" style="display:inline;">
                                <input type="hidden" name="noteId" value="<%= note.getId() %>">
                                <button type="submit" class="btn btn-danger btn-sm">
                                    <span class="glyphicon glyphicon-trash"></span> Delete
                                </button>
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
        <div class="dropdown dropdown-right">
            <button class="btn btn-success" type="button" data-toggle="dropdown">
                <span class="glyphicon glyphicon-plus"></span> Add
            </button>
            <ul class="dropdown-menu">
                <li><a href="addItem?parentFolderId=<%= currentFolder.getId() %>&itemType=note">Add Note</a></li>
                <li><a href="addItem?parentFolderId=<%= currentFolder.getId() %>&itemType=folder">Add Folder</a></li>
            </ul>
        </div>

        <%
            if (currentFolder.getParentId() != null) {
        %>
                <a href="displayFolder?folderId=<%=currentFolder.getParentId()%>" class="btn btn-primary">
                    <span class="glyphicon glyphicon-arrow-left"></span> Back
                </a>
        <%  } %>
    </div>
</div>

<jsp:include page="/footer.jsp"/>
</body>
</html>
