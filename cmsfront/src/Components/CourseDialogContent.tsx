import { CourseResponse } from "../type";

type DialogFormProps = {
  course: CourseResponse;
  handleChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
};
function CourseDialogContent({ course, handleChange }: DialogFormProps) {
  return (
    <>
        <input
          placeholder="Code"
          name="code"
          value={course.code}
          onChange={handleChange}
        />
        <br />
        <input
          placeholder="Title"
          name="title"
          value={course.title}
          onChange={handleChange}
        />
        <br />
        <input
          placeholder="status"
          name="status"
          value={course.status}
          onChange={handleChange}
        />
        <br />
        <input
          placeholder="Year"
          name="year"
          value={course.year}
          onChange={handleChange}
        />
        <br />
        <input
          placeholder="season"
          name="season"
          value={course.season}
          onChange={handleChange}
        />
        <br />
    </>
  );
}
export default CourseDialogContent;
