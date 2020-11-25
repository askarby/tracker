export function enumValuesOf<T>(input: T, ...excluding: any[]): T[] {
  let values = Object.values(input);
  if (excluding.length > 0) {
    values = values.filter((each) => !excluding.includes(each));
  }
  return values;
}
