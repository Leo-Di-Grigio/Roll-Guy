-- arr[0] == type
-- arr[1] == context
-- arr[2] == user
-- arr[3] == target

function execute(arr)
	if(arr[1] == CONTEXT_GO_USE) then
		if(arr[2] ~= nil and arr[3] ~= nil) then
			local user = arr[2];
			local teleport = arr[3];
			location:teleport(user, teleport);
		end
	end
end