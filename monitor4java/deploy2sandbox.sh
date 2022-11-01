sandbox_module_path=$1

mvn clean package

if [[ -z $sandbox_module_path ]];
then
    sandbox_module_path="~/sandbox/sandbox-module/"
    echo "sandbox_module_path is empty, use default path: ${sandbox_module_path}"
    cp ./target/monitor4java-*-jar-with-dependencies.jar ~/sandbox/sandbox-module/
else
    echo "sandbox_module_path is not empty."
    cp ./target/monitor4java-*-jar-with-dependencies.jar ${sandbox_module_path}
fi

echo "deploy2sandbox finish. use path: ${sandbox_module_path}"