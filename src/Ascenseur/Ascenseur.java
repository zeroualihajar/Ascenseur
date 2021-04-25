package Ascenseur;

import java.util.ArrayList;
import javax.swing.JPanel;

public class Ascenseur extends Thread {
	private int xAsc, yAsc; //Coordonnees de l'ascenseur
	private JPanel asc;    // le panel d'ascenseur
	private Personne person;
	private ArrayList<Personne> personnesAsc = new ArrayList<Personne>(); //Personnes existe dans dl'ascenseur
	
	//------- Constructeur ------------
	public Ascenseur(JPanel asc,Personne person)
	{
		this.asc = asc;
		this.xAsc = asc.getLocation().x;
		this.yAsc = asc.getLocation().y;
		this.person = person;
		
	}
	
	//----- Fonction : monter() ---------
	synchronized void monter()
	{
		this.asc.setLocation(this.asc.getLocation().x, this.asc.getLocation().y - 100);
	}
	
	//------- Fonction : descendre() ----
	synchronized void descendre()
	{
		this.asc.setLocation(this.asc.getLocation().x, this.asc.getLocation().y + 100);
	}
	
	
	///------- Fonction : run() --------------
	@Override
	public void run() 
	{
		
		if(person.getAppel() == true)
		{
			System.out.println("-------------| L'appel est vraie! |-------------");
			System.out.println("              --------------------");
			
				if(person.getEtage() < this.getYAsc())
				{
					System.out.println("asc < personne => Monter");
					int j = person.allerInv(person.getEtage()) - this.allerInv(this.getYAsc());
					System.out.println("Nombre d'itération : " + j);
					
					for(int i = 0; i < j; i++)
					{
						this.monter();
						this.setYAsc(this.asc.getLocation().y);
						try 
						{
							sleep(500);
						} 
						catch (Exception e) 
						{
							e.printStackTrace();
						}
					}
					//---- verifier capacite -----------
					if(personnesAsc.size() < 4)
					{
						System.out.println("Entrez : il reste encore des places");
						
						this.personnesAsc.add(person);
						person.rentrer(this.asc);
						try 
						{
							Personne.sleep(500);
						} 
						catch (InterruptedException e) 
						{
							e.printStackTrace();
						}
						if(person.getDestination() < this.getYAsc())
						{
							int k = this.allerInv(person.getDestination()) - this.allerInv(this.getYAsc());
							System.out.println("Nombre d'itération : " + k);
							
							for(int i = 0; i < k; i++)
							{
								this.monter();
								try
								{
									sleep(500);
								}
								catch (InterruptedException e) 
								{
									e.printStackTrace();
								}
							}
							person.sortir(this.asc);
							this.personnesAsc.remove(person);
						}
						if(person.getDestination() > this.getYAsc())
						{
							int h = this.allerInv(this.getYAsc()) - this.allerInv(person.getDestination());
							System.out.println("Nombre d'itération : " + h);
							
							for(int i = 0; i < h; i++)
							{
								this.descendre();
								try
								{
									sleep(500); 
								}
								catch (InterruptedException e) 
								{
									e.printStackTrace();
								}
							}
							person.sortir(this.asc);
							this.personnesAsc.remove(person);
						}
					}
					else
					{
						try 
						{
							person.wait();
						} 
						catch (InterruptedException e) 
						{
							e.printStackTrace();
						};
					}
				}
				
				if(person.getEtage() > this.getYAsc())
				{
					System.out.println("asc > personne => Descendre");
					int l = this.allerInv(this.getYAsc()) - person.allerInv(person.getEtage());
					System.out.println("Nombre d'itération : " + l);
					
					for(int i = 0; i < l; i++)
					{
						this.descendre();
						this.setYAsc(this.asc.getLocation().y);
						try 
						{
							sleep(500);
						} 
						catch (InterruptedException e) 
						{
							e.printStackTrace();
						}
					}
					//------- verifier capacite -------
					if(personnesAsc.size() < 4)
					{
						this.personnesAsc.add(person);
						person.rentrer(this.asc);
						try 
						{
							Personne.sleep(500);
						} 
						catch (InterruptedException e) 
						{
							e.printStackTrace();
						}
						if(person.getDestination() < this.getYAsc())
						{
							int m = this.allerInv(person.getDestination()) - this.allerInv(this.getYAsc());
							System.out.println("Nombre d'itération : " + m);
							
							for(int i = 0; i < m; i++)
							{
								this.monter();
								try
								{
									sleep(500);
								}
								catch (InterruptedException e) 
								{
									e.printStackTrace();
								}
							}
							person.sortir(this.asc);
							this.personnesAsc.remove(person);
						}
						if(person.getDestination() > this.getYAsc())
						{
							int n = this.allerInv(this.getYAsc()) - this.allerInv(person.getDestination());
							System.out.println("Nombre d'itération : " + n);
							
							for(int i = 0; i < n; i++)
							{
								this.descendre();
								try
								{
									sleep(500); 
								}
								catch (InterruptedException e) 
								{
									e.printStackTrace();
								}
							}
							person.sortir(this.asc);
							this.personnesAsc.remove(person);
						}
					} 
					else
					{
						try 
						{
							person.wait();
						} 
						catch (InterruptedException e) 
						{
							e.printStackTrace();
						}
					}
				}
			}
		
	}

	//-------- y => etage -----------
	public int allerInv(int yEtage)
	{
		int etage = 0;
		switch(yEtage)
		{
			case 510 :
				etage = 0;
				break;
			case 410 :
				etage = 1;
				break;
			case 310 :
				etage = 2;
				break;
			case 210 :
				etage = 3;
				break;
			case 110 :
				etage = 4;
				break;
			case 10 :
				etage = 5;
				break;
		}
		return etage;
	}
	
	//----------- Getters ---------
	public int getXAsc()
	{
		return this.xAsc;
	}
	
	public synchronized int getYAsc()
	{
		return this.yAsc;
	}
	
	//--------- Setters --------------
	public void setXAsc(int x)
	{
		this.xAsc = x;
	}
	
	public void setYAsc(int y)
	{
		this.yAsc = y;
	}
	
	
}
