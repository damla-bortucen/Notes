<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Set" %>
<%@ page import="ucl.ac.uk.classes.Note" %>
<%@ page import="ucl.ac.uk.classes.Image" %>

<%
    Note note = (Note) request.getAttribute("note");
    boolean isNew = (Boolean) request.getAttribute("isNew");
    Set<String> categories = (Set<String>) request.getAttribute("categories"); // Available categories
    Set<String> noteCategories = note.getCategories();
%>

<html>
<head>
    <meta charset="UTF-8">
    <title>Add Notes</title>
    <jsp:include page="/meta.jsp"/>
</head>

<body>
<jsp:include page="/header.jsp"/>

<div class="container">
    <h2>Start Taking Notes:</h2>
    <form method="post" action="/saveNote" enctype="multipart/form-data">

        <input type="hidden" name="folderId" value="<%= request.getParameter("parentFolderId") %>">

        <% if (!isNew) { %>
            <input type="hidden" name="noteId" value="<%= note.getId() %>">
        <% } %>

        <div class="form-group">
            <label for="note">Note Name:</label>
            <input type="text" id="noteName" name="noteName" value="<%= isNew ? "" : note.getName() %>" class="form-control" required>
        </div>

        <div class="form-group">
            <label for="content">Note Content:</label>
            <textarea class="form-control" rows="5" id="content" name="content"><%= isNew ? "" : note.getContent() %></textarea>
        </div>

        <div>
            <h4>Current Image:</h4>
            <%
                Image image = note.getImage();
                if (image != null) {
            %>
                    <img src="<%= image.getImageName() %>" style="max-width: 300px; max-height: 300px;">
                    <p>Currently selected image</p>
                    <label for="removeImage">Remove current image</label>
                    <input type="checkbox" id="removeImage" name="removeImage">
            <%
                } else {
            %>
                    <p>No image uploaded for this note.</p>
            <%
                }
            %>
        </div>

        <h4>Upload a New Image:</h4>
        <input type="file" name="image" class="form-control">
        <br>

        <div class="form-check">
            <label for="noteCategories">Categories:</label>
            <%
                for (String category : categories) {
            %>
                    <input class="form-check-input" type="checkbox" id="<%= category %>" name="noteCategories" value="<%= category %>"
                        <% if (noteCategories.contains(category)) { %> checked <% } %>>
                    <label class="form-check-label"><%= category %></label>
            <%  } %>
        </div>

        <input class="form-control btn-success" type="submit" value="Save">
    </form>
</div>

<!-- jsp:include page="/footer.jsp" -->
</body>
</html>