package gen;

import javafactura.businessLogic.JavaFactura;
import javafactura.businessLogic.exceptions.InvalidCredentialsException;
import javafactura.businessLogic.exceptions.NoSuchIndividualException;
import javafactura.businessLogic.exceptions.NotContribuinteException;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

public class Gen {

    public static void main(String[] args){
        JavaFactura javaFactura = JavaFactura.loadState();
        try{
            Random r = new Random();
            for(int i = 0; i < 20; i++){
                String nif = String.format("%d", r.nextInt(18) * 2);
                try{
                    javaFactura.login(nif, "pass");
                }catch(InvalidCredentialsException ignored){
                }
                String client = String.format("%d", (r.nextInt(18) * 2) - 1);
                try{
                    out.println(javaFactura.emitirFactura(client, r.nextFloat() * 200, nif + "->" + client));
                }catch(NotContribuinteException | NoSuchIndividualException e){
                    e.printStackTrace();
                }
                out.println(nif + "->" + client);
                TimeUnit.SECONDS.sleep(10);
            }
        }catch(InterruptedException ignored){
        }finally{
            try{
                javaFactura.saveState();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
