import java.util.Map;
import java.util.TreeMap;

public class FileSystem {
    //Aceasta clasa modeleaza design pattern-ul de Singleton deoarece
    //am nevoie de un sistem de fisiere unic in aceasta tema.
    private static FileSystem fileSystem = null;
    private FileSystem(){
        root = new Directory();
        currentDir = root;
    }

    public static FileSystem getInstance(){
        if(fileSystem == null) {
            fileSystem = new FileSystem();
        }

        return fileSystem;
    }


    //De aici incepe functionalitatea acestei clase care controleaza sistemul
    //de fisiere
    private static Directory root;
    private static Directory currentDir;


    public static Directory getRoot(){return root;}
    public static Directory getCurrentDir(){return currentDir;}
    public static void setCurrentDir(Directory currentDir) {FileSystem.currentDir = currentDir;}



    public static String computePath(Leaf leaf) {
        if(leaf == null){
            System.out.println("Eroare la calcularea path-ului");

            return "EROARE";
        }

        Directory parent = leaf.getParent();
        String path = parent.getFullPath();

        return path;
    }

    public static Directory getDirectoryFrom(String path){
        if(path.equals("") || path.equals("/")){
            return root;
        }
        if(path.equals(".")){
            return currentDir;
        }


        Directory directory = currentDir;

        String[] parts = path.split("/");

        for(String part : parts){
            if(directory == null){
                return directory;
            }

            if(part.equals(".")){
                continue;
            }

            if(part.equals("..")){
                directory = directory.getParent();
                continue;
            }

            if(part.equals("")){
                directory = root;
                continue;
            }

            //caut directorul in interiorul directorului curent
            //rezultatul intors este sub forma de frunza
            //nu se stie inca daca e director sau fisier
            Leaf leaf = directory.get(part);
            if(leaf instanceof Directory){
                directory = (Directory) leaf;
            }else{
                directory = null;
                break;
            }


        }

        return directory;
    }

    public static String extractParentPathFrom(String path) {
        String[] parts = path.split("/");
        if(parts.length == 0){
            return "/";
        }

        if(parts.length == 1){
            return ".";
        }

        String parentPath = parts[0];
        for(int i = 1; i < parts.length - 1; i++){
            parentPath += "/" + parts[i];
        }


        return parentPath;
    }

    public static String getFileName(String path){
        String[] parts = path.split("/");
        if(parts.length == 0){
            return path;
        }

        return parts[parts.length - 1];
    }
}



/*==============================================*/
/*==================== FILE ====================*/
/*==============================================*/

class File implements Leaf{
    private String name;
    private Directory parent;
    private String path;
    private String fullPath;
    private String fileType;

    public File(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
        this.path = this.parent.getFullPath();
        this.fileType = "File";

        if(this.parent == FileSystem.getRoot()){
            this.fullPath = this.path + this.name;
        }else {
            this.fullPath = this.path + "/" + this.name;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String getFullPath() {
        return fullPath;
    }

    @Override
    public void resetFullPath() {
        if (this.parent == FileSystem.getRoot()) {
            this.fullPath = "/" + this.name;
        } else {
            this.fullPath = this.parent.getFullPath() + "/" + this.name;
        }
    }

    @Override
    public String getFileType() {
        return fileType;
    }

    @Override
    public Directory getParent() {
        return parent;
    }

    @Override
    public void setParent(Directory destDir) {
        this.parent = destDir;
    }

    @Override
    public Leaf clone(){
        Leaf copyLeaf = null;
        try{
            copyLeaf = (Leaf) super.clone();
        }catch (CloneNotSupportedException e){
            System.out.println("Nu s-a clonat " + this.name);
            return null;
        }

        return copyLeaf;
    }
}


/*===============================================*/
/*================== Directory ==================*/
/*===============================================*/

class Directory implements Leaf{
    private String name;
    private Directory parent;
    private String path;
    private String fullPath;
    private String fileType;
    private TreeMap<String, Leaf> inside;

    public Directory() {
        this.name = "root";
        this.parent = null;
        this.path = "/";
        this.fullPath = this.path;
        this.inside = new TreeMap<String, Leaf>();
        this.fileType = "Directory";
    }

    public Directory(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
        this.path = this.parent.getFullPath();
        this.inside = new TreeMap<String, Leaf>();
        this.fileType = "Directory";

        if(this.parent == FileSystem.getRoot()){
            this.fullPath = this.path + this.name;
        }else {
            this.fullPath = this.path + "/" + this.name;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String getFullPath() {
        return fullPath;
    }

    @Override
    public void resetFullPath() {
        if (this.parent == FileSystem.getRoot()) {
            this.fullPath = "/" + this.name;
        } else {
            this.fullPath = this.parent.getFullPath() + "/" + this.name;
        }
    }

    @Override
    public String getFileType() {
        return fileType;
    }

    @Override
    public Directory getParent() {
        return parent;
    }

    @Override
    public void setParent(Directory destDir) {
        this.parent = destDir;
    }

    public TreeMap<String, Leaf> getInside() {
        return inside;
    }

    public void setInside(TreeMap<String, Leaf> inside) {
        this.inside = inside;
    }

    @Override
    public Leaf clone(){
        Directory copyLeaf = null;
        try{
            copyLeaf = (Directory) super.clone();
        }catch (CloneNotSupportedException e){
            System.out.println("Nu s-a clonat " + this.name);
            return null;
        }

        TreeMap<String, Leaf> copyInside = null;
        TreeMap<String, Leaf> originalInside = null;
        originalInside = copyLeaf.getInside();
        copyInside = (TreeMap<String, Leaf>) originalInside.clone();
        for (Map.Entry<String, Leaf> entry : copyInside.entrySet()) {
            Leaf leaf = entry.getValue();
            String key = entry.getKey();
            Leaf newLeaf = leaf.clone();
            newLeaf.setParent(this);
            copyInside.replace(key, newLeaf);
        }
        copyLeaf.setInside(copyInside);

        return (Leaf)copyLeaf;
    }

    public Leaf get(String name){
        return inside.get(name);
    }

    public int add(Leaf leaf){
        String leafName = leaf.getName();

        //Daca frunza exista returnez un cod de eroare
        if(inside.containsKey(leafName)){
            return -1;
        }

        //Daca nu exista atunci o adaug in interiorul directorului
        inside.put(leaf.getName(), leaf);

        return 0;
    }


    public String listFiles() {
        String text = this.fullPath + ":\n";
        boolean writeSpace = false;
        for (Map.Entry<String, Leaf> entry : inside.entrySet()) {
            Leaf leaf = entry.getValue();

            if(writeSpace){
                text += " " + leaf.getFullPath();
            }else{
                writeSpace = true;
                text += leaf.getFullPath();
            }

        }

        return text + "\n";
    }

    public String listFilesRecursive() {

        String text = this.listFiles();
        for (Map.Entry<String, Leaf> entry : inside.entrySet()) {
            Leaf leaf = entry.getValue();

            if(leaf instanceof Directory){
                text += "\n";
                text += ((Directory) leaf).listFilesRecursive();
            }
        }

        return text;
    }

    public void resetPathAndParents() {
        //resetare campuri pentru acest obiect
        this.path = this.parent.getFullPath();
        this.resetFullPath();

        for (Map.Entry<String, Leaf> entry : inside.entrySet()) {
            Leaf leaf = entry.getValue();
            //Resetez parintele indiferent daca e fisier sau director
            leaf.setParent(this);

            if(leaf instanceof Directory){
                //Daca e director atunci apelez recursiv aceeasi metoda pentru
                //nodurile din interiorul acestuia
                ((Directory)leaf).resetPathAndParents();
            }else{
                //Daca e fisier atunci se reseteaza doar path-urile lui
                ((File)leaf).setPath(this.getFullPath());
                ((File)leaf).resetFullPath();
            }
        }
    }

    public void remove(Leaf originalLeaf) {
        inside.remove(originalLeaf.getName());
    }

    public boolean hasCurrentDir() {
        Directory currentDir = FileSystem.getCurrentDir();

        //Daca chiar acest folder este folderul curent atunci intorc adevarat
        if(this == currentDir){
            return true;
        }

        boolean found = false;
        //Altfel mai caut in interiorul acestui director
        for (Map.Entry<String, Leaf> entry : inside.entrySet()) {
            Leaf leaf = entry.getValue();

            if(leaf instanceof Directory){
                found = ((Directory)leaf).hasCurrentDir();
                if(found){
                    return found;
                }
            }
        }

        return found;
    }

    public Directory getDirectory(String name) {
        //Daca acest director este cel cautat il intorc
        if(this.name.equals(name)){
            return this;
        }

        Directory found = null;
        //Altfel mai caut in interiorul acestui director
        for (Map.Entry<String, Leaf> entry : inside.entrySet()) {
            Leaf leaf = entry.getValue();

            //Altfel mai caut in interiorul acestuia daca este director
            if(leaf instanceof Directory){
                found = ((Directory)leaf).getDirectory(name);
                if(found != null){
                    return found;
                }
            }
        }

        return found;
    }
}