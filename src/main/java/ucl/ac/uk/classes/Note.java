package ucl.ac.uk.classes;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Note extends Item
{
    private String content;
    private Set<String> categories;
    private String imagePath;

    public Note()
    {
        super("", ""); // default values for id and name
        this.content = "";
        this.categories = new HashSet<>();
        this.imagePath = null;
    }

    public String getContent() {return content;}

    public void setContent(String content) {this.content = content;}

    public String formatContent()
    {
        String urlRegex = "(https?://[\\w./-]+)";
        Pattern pattern = Pattern.compile(urlRegex);
        Matcher matcher = pattern.matcher(content);

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String url = matcher.group(); //Returns the input subsequence matched by the previous match.
            System.out.println(url);
            matcher.appendReplacement(sb, "<a href='" + url + "' target='_blank'>" + url + "</a>");
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public Set<String> getCategories() {return categories;}

    public void setCategories(Set<String> categories) {this.categories = categories;}

    public void addCategory(String category) {categories.add(category);}

    public void removeCategory(String category) {categories.remove(category);}

    public String getImagePath() {return imagePath;}

    public void setImagePath(String imagePath) {this.imagePath = imagePath;}
}
