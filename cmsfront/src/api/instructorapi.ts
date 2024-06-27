import axios from "axios";
import { InstructorResponse } from "../type";

interface AssignInstructorPayload {
  instructorId: number;
  courseId: number;
}

export const getallinstructors = async (): Promise<InstructorResponse[]> => {
  const response = await axios.get(
    `${import.meta.env.VITE_API_URL}/api/instructors/getInstructors`
  );
  return response.data;
};

export const addCourseToInstructor = async ({
  instructorId,
  courseId,
}: AssignInstructorPayload): Promise<InstructorResponse> => {
  const response = await axios.put(
    `${
      import.meta.env.VITE_API_URL
    }/api/instructors/${instructorId}/${courseId}/addCourse`,
    {
      headers: {
        "Content-Type": "application/json",
      },
    }
  );
  return response.data;
};
