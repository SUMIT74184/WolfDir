import { Inter, Merriweather } from "next/font/google"
import { ThemeProvider } from "@/components/theme-provider"
import { Navbar } from "@/components/navbar"
import { Footer } from "@/components/footer"
import "./globals.css"

const inter = Inter({ subsets: ["latin"] })
const merriweather = Merriweather({
  weight: ["300", "400", "700", "900"],
  subsets: ["latin"],
  variable: "--font-serif",
})

export const metadata = {
  title: "Blogify - Where Ideas Come to Life",
  description: "Join our community of writers and readers.",
}

export default function RootLayout({ children }) {
  return (
    // suppressHydrationWarning is REQUIRED on <html>
    <html lang="en" suppressHydrationWarning>
      <body className={`${inter.className} ${merriweather.variable} font-sans antialiased`}>
        <ThemeProvider 
          attribute="class" 
          defaultTheme="dark" 
          enableSystem={true}
          storageKey="blogify-theme"
        >
          <div className="flex min-h-screen flex-col">
            <Navbar />
            <main className="flex-1">{children}</main>
            <Footer />
          </div>
        </ThemeProvider>
      </body>
    </html>
  )
}