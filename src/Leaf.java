public interface Leaf extends Cloneable{
    public String getName();
    public String getPath();
    public void setPath(String path);
    public String getFullPath();
    public void resetFullPath();
    public String getFileType();
    public Directory getParent();
    public void setParent(Directory destDir);
    public Leaf clone();
}
