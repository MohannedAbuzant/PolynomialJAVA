/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javahw;
import java.util.ArrayList;

/**
 *
 * @author Dr.Abuzant
 */

 class  Term {
        private double cof;
        private int exp;
        public Term(double c,int e){
        setcof(c);
        setexp(e);
    }
        public void setcof(double c){
            cof = c;
        }
        public double getcof(){
            return cof;
        }
        public void  setexp(int e){
            exp=e;
            
        }
        public  int getexp(){
            return exp;
        }
        public String toString(){
            return getcof()+"X^"+getexp();
        }
    }
class Polynomial  {
        private ArrayList <Term> Terms;
        public Polynomial(){
            
             Terms= new ArrayList<Term>();
        }
        public Polynomial(String x){
                this();
              x= x.replace("-","+-");
              System.out.println(x);
                String [] sp = x.split("[+]");           
                           
              for(String l:sp){
               
		    double cof = 0.0;
		    int exp=0;
                    l.trim();
                    int index=l.indexOf("x^");
                    if (l.isEmpty())
                        continue;
                    
                    if (index==-1)
                    {
                      index=l.indexOf('x');
                      if (index==-1){
                         cof=Integer.parseInt(l);
                         exp=0;
                      }
                      else{
                          exp=1;
                          if (l.substring(0,index).isEmpty() || l.startsWith("+x")
                                                             || l.startsWith("-x"))
                              cof=l.startsWith("-x")? -1: 1;
                          else
                           cof=Float.parseFloat(l.substring(0,index));
                      }
                    }
                    else{
                     if (index==0){
                            cof=1;
                            exp=Integer.parseInt(l.substring(index+2));
                     }   
                     else if (l.startsWith("-x") || l.startsWith("+x")){
                         cof=l.startsWith("-x")? -1: 1;  
                         exp=Integer.parseInt(l.substring(index+2));
                     }
                     else{
                       cof=Float.parseFloat(l.substring(0, index));
                       exp=Integer.parseInt(l.substring(index+2));
                         
                     }
                    } 
              
                  
                   addTerm(cof,exp);

		}
                
        }
        
        public void addTerm(double c,int e){
             Term x=new Term (c,e);
             Terms.add(x);
   
        }
        public String toString(){
            String s = "";
            for(int i=0; i<Terms.size();i++){
                Term x= Terms.get(i);
                if( x.getexp()==0){
                    s+=(x.getcof() >0 ? "+" : "") +x.getcof();
                    
                }
                else if(x.getcof()==1 && x.getexp()!=0){
                    s+="+x^"+x.getexp();
                }
                else if(x.getcof()==-1 && x.getexp()!=0){
                    s+="-x^"+x.getexp();
                }
                else{
                    s+=x.getcof()<0 ? "": "+";
                    s+=x.getcof()+"x^"+x.getexp();
                }
            }
            return s;
            
        }
     public Polynomial add(Polynomial x){
         Polynomial p= new Polynomial();
         for(int i=0;i<Terms.size();i++){
             boolean found=false;
             int j;
             for( j=0;j<x.Terms.size() && !found;j++){
                 if(Terms.get(i).getexp()==x.Terms.get(j).getexp())
                   found=true;
             }          
             if (found)
               p.addTerm(Terms.get(i).getcof()+x.Terms.get(j-1).getcof(),Terms.get(i).getexp());
             else
               p.addTerm(Terms.get(i).getcof(),Terms.get(i).getexp());
          }
         
         for(int i=0;i<x.Terms.size();i++){
             boolean found=false;
             for(int j=0;j<p.Terms.size() && !found;j++){
                if(p.Terms.get(j).getexp()==x.Terms.get(i).getexp())
                  found =true;
             }
             if (!found)
               p.addTerm(x.Terms.get(i).getcof(), x.Terms.get(i).getexp());
             
         }
             
         return p;
     }   
     public Polynomial subtract(Polynomial x){
         Polynomial p= new Polynomial();
         for(int i=0;i<Terms.size();i++){
             boolean found=false;
             int j;
             for( j=0;j<x.Terms.size() && !found;j++){
                 if(Terms.get(i).getexp()==x.Terms.get(j).getexp())
                   found=true;
             }          
             if (found)
               p.addTerm(Terms.get(i).getcof()-x.Terms.get(j-1).getcof(),Terms.get(i).getexp());
             else
               p.addTerm((Terms.get(i).getcof()),Terms.get(i).getexp());
          }
         
         for(int i=0;i<x.Terms.size();i++){
             boolean found=false;
             for(int j=0;j<p.Terms.size() && !found;j++){
                if(p.Terms.get(j).getexp()==x.Terms.get(i).getexp())
                  found =true;
             }
             if (!found)
               p.addTerm(x.Terms.get(i).getcof(), x.Terms.get(i).getexp());
             
         }
             
         return p;
     }   
     public Polynomial multiply(Polynomial s){
         Polynomial m = new Polynomial();
         for(int i=0;i<s.Terms.size();i++){
             for(int j=0;j<Terms.size();j++){
          
                     m.addTerm(Terms.get(j).getcof()*s.Terms.get(i).getcof(),Terms.get(j).getexp()+s.Terms.get(i).getexp());
              
             }
         }
         
         
         for(int i=0;i<m.Terms.size();i++){
             
             for(int j=0;j<m.Terms.size();j++){
                   if (m.Terms.get(i).getexp()==m.Terms.get(j).getexp()&& i!=j)
                   {  m.Terms.set(i, new Term(m.Terms.get(i).getcof()+
                                          m.Terms.get(j).getcof() ,
                                    m.Terms.get(i).getexp()));
                       m.Terms.remove(j);
                      
                   } 
                             
             }
         }
         
         
         return m;
     }
     public boolean equal(Polynomial f){
         boolean equal=true;
         if(Terms.size()!= f.Terms.size()){
             
             return false;
         }
         
         for(int i=0;i<Terms.size()&& equal;i++){
             equal=false;
            for(int j=0;j<f.Terms.size()&&!equal;j++){
                 
                if((Terms.get(i).getcof()==f.Terms.get(j).getcof())&& (Terms.get(i).getexp()==f.Terms.get(j).getexp())){
                   equal=true;
                   
                }
           }
         }
         return equal;
     }
    }
public class JavaHW {

    /**
     * @param args the command line arguments
     */ 
   
    
    public static void main(String[] args) {
       Polynomial p1 = new Polynomial("4x^4-3x^3+2x^2+4");
       Polynomial p2 = new Polynomial("x^5-3x^4+2x^3+4x-6");
       Polynomial p3;
       p3 = new Polynomial();
       p3.addTerm(-1, 5);
       p3.addTerm(7,4);
       p3.addTerm(-5, 3);
       p3.addTerm(2,2);
       p3.addTerm(-4, 1);
       p3.addTerm(10, 0);
       System.out.println(p3);
      Polynomial p4 = new Polynomial();
      p4=p1.add(p2);
       Polynomial px = new Polynomial();
      Polynomial p6 = new Polynomial();
       System.out.println("p4 =p1 + p2 =" + p4);
       px = p1.subtract(p2);
       System.out.println("px =p1 - p2 =" + px);

       p6=p2.subtract(p3);
       System.out.println("p6 =p2 - p3 =" + p6);
       if(px.equals(p3))
       System.out.println("px is equal to p3");
       else
       System.out.println("px is not equal to p3");
     Polynomial p= new Polynomial();
    p= p1.multiply(p4);
    System.out.println("p = p1*p4 ="+p);
        Polynomial p5= new Polynomial();
        p5=p2.add(p3);
        System.out.println("p5 = p2+p3 ="+p5);
        
    }
    
}
