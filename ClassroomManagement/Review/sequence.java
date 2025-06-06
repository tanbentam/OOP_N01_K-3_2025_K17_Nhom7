package ClassroomManagement.Review;

public class sequence {
      private Object[] objects; //array of Object
      private int next = 0;
      public sequence(int size) 
            { 
               objects = new Object[size];   
            }
      
      public void add(Object x) 
            { //x dont have length
               if (next < objects.length) 
               { 
                  objects[next] = x; 
                  next++;
               } 
            }

        private class SSelector implements selector {
         //Selector is an interface
         int i = 0;
         public boolean end() 
            { 
               return i == objects.length; 
            }
         public Object current() { 
               return objects[i]; 
            }
         public void next() {
               if (i < objects.length) i++;
            }
            }
         public selector getSelector() { 
         return new  SSelector();
            }
         }


