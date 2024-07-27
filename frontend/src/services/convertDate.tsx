export const convertDateToBrasil = (date: Date) => {
    const novaData = new Date(date.getTime() - 180 * 60 * 1000);
    return novaData;
}