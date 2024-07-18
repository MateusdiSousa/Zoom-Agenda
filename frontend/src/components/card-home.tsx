import { Link } from "react-router-dom";

interface Props {
    icon: any
    titleCard: string
    buttonText: string
    buttonLink: string
}


export const CardHome = (props: Props) => {
    return (
        <>
            <div className="flex flex-col space-y-5 p-8 rounded-3xl">
                <div className="flex justify-center">
                    {props.icon}
                </div>
                <span> {props.titleCard} </span>
                <Link to={props.buttonLink}>
                    <button
                        className="rounded-lg py-4 px-20 font-sans text-xs font-bold uppercase transition-all hover:shadow-lg focus:opacity-[0.85]  "
                    >
                        {" "} {props.buttonText} {" "}
                    </button>
                </Link>
            </div>
        </>
    );
};