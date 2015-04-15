function execute()
	console:print(global:getInt("v31", 42));
	console:print(global:getFlag("v21", false));
	console:print(global:getFlag("v31", true));
	console:print(global:getText("v41", "text"));
	console:print(global:getText("v51", 'test'));
end