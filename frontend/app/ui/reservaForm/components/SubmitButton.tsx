export default function SubmitButton({ active, text }: { active: boolean; text: string }){
    return (
        <div>
                    <button
                        type="submit"
                        className={`w-full bg-blue-600 text-white px-3 py-2 rounded transition ${
                            active
                                ? "hover:bg-blue-700"
                                : "opacity-50 cursor-not-allowed"
                        }`}
                        disabled={!active}
                    >
                        {text}
                    </button>
                </div>
    );
}