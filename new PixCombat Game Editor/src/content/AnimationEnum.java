package content;

public enum AnimationEnum 
{
	STAND(0,"stand"),
	MOVE(1,"move"),
	JUMPING(2,"jumping"),	
	BASICATTACK(3,"basicAttack"),
	SPECIALATTACK1(4,"specialAttack1"),
	SPECIALATTACK2(5,"specialAttack2"),
	SPECIALATTACK3(6,"specialAttack3"),
	ISHIT(7,"isHit"),
	KNOCKBACK(8,"knockBack"),
	KNOCKEDOUT(9,"knockedOut"),
	AVATAR(10,"avatar"),
	BASICATTACK1(11,"basicAttack1"),
	JUMPATTACK(12,"jumpAttack"),
	RETREATING(13,"retreating"),
	DASHING(14,"dashing"),
	DEFENDING(15,"defend"),
	SPECIALATTACK4(16,"specialAttack4"),
	SPECIALATTACK5(17,"specialAttack5"),
	BASICATTACK21(18,"basicAttack21"),
	BASICATTACK22(19,"basicAttack22"),
	BASICATTACK23(20,"basicAttack23"),
	SPECIALATTACK6(21,"specialAttack6"),
	SPECIALATTACK7(22,"specialAttack7"),
	INTRO(23,"intro"),
	WIN(24,"win"),
	DEAD(25,"dead"),
	RUNATTACK1(26,"runAttack1"),
	RUNATTACK2(27,"runAttack2"),
	JUMPRECOVER(28,"jumpRecover"),
	AIR_DEFENDING(29,"airDefend"),
	AIR_SPECIALATTACK1(30,"airSpecialAttack1"),
	KNOCKBACKRECOVER(31,"knockBackRecover"),
	JUMPFALL(32,"jumpFall");
	
	private String name;
	private int id;
	AnimationEnum(int id, String name)
	{
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
	
}
