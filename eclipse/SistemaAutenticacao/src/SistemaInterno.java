
public class SistemaInterno {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Autenticavel [] autenticaveis = new Autenticavel[3];
		autenticaveis[0] = new Diretor();
		autenticaveis[1] = new Gerente();
		autenticaveis[2] = new Cliente();
		
		for (int i = 0; i < autenticaveis.length; i++)
			login(autenticaveis[i]);


	}
	
	public static void login(Autenticavel funcionario){
		funcionario.autentica(124);
	}

}
