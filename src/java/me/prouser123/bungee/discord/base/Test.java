package me.prouser123.bungee.discord.base;

import me.prouser123.bungee.discord.Main;

public class Test implements BaseCommand {//, MessageCreateListener {

	public Test() {
		base base = this.createBase();
		base.command = "Test";
		base.helpText = "Hewo";
		Main.inst().getLogger().info("Test1 " + base.command + " HP: " + base.helpText + this.getClass().getName());
	}
	
	public class Test2 implements BaseCommand {
		public Test2() {
			base base = this.createBase();
			base.command = "Test2";
			Main.inst().getLogger().info("Test2" + base.command + this.getClass().getName());
		}
	}
	
}