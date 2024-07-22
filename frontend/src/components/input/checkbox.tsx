interface CheckboxProps {
    name: string
    value : boolean 
    setvalue : Function
}

export function Checkbox(props : CheckboxProps){
    return(
    <div className="flex space-x-3">
        <label>{props.name}{props.value ? "Yes" : "No"}</label>
        <input className="bordaInput items-center w-4 h-auto p-1 rounded-md border-2"
          checked={props.value}  type="checkbox" onChange={(e) => props.setvalue(e.target.checked)} />
    </div>
    )
}