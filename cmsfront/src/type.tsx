export type CourseResponse = {
  id: number;
  code: string;
  title: string;
  season: string;
  year: string;
  instructorName: string;
  instructorId: number | null; // Add this line
  status: string;
};

export type InstructorResponse = {
  id: number;
  name: string;
  email: string;
  department: string;
  faculty: string;
}